function PagamentoCtrl($scope, $http, $dialog, $window, $timeout, ContratoService, PagamentoService) {
	
	$scope.orderBy = "id";
	$scope.orderBy2 = "dtVencimento";
	
	$scope.filter = new Object();
	$scope.filter.ano =  moment(new Date()).year();
	$scope.filter.count = 0;
	$scope.filter.startRow = 1;
	$scope.filter.pageSize = 5;
	
	$scope.filter2 = new Object();
	$scope.filter2.contratoId = 0;
	$scope.filter2.count = 0;
	$scope.filter2.startRow = 1;
	$scope.filter2.pageSize = 25;
	
	//grid select
	$scope.pagamentos = [];
	$scope.pagamentosSelected = 0;
	
	$scope.currentContrato = new Object();
	
	$scope.numPages = function() {
		return Math.ceil($scope.filter.count / $scope.filter.pageSize);
	};
	
	$scope.numPages2 = function() {
		return Math.ceil($scope.filter2.count / $scope.filter2.pageSize);
	};
	
	$scope.findByFilter = function() {
		ContratoService.findByFilter($scope.filter, function(response) {
			$scope.contratos = response.data;
			$scope.filter.count = response.count;
		});
	};
	
	
	$scope.$watch('filter.startRow + filter.pageSize', function() {
		$scope.findByFilter();
	});
	
	$scope.findByFilter();
	
	$scope.createPayment = function(contrato) {
		PagamentoService.createByContrato(contrato.id, 
			function(response) {
				noty({layout: 'topRight', type: 'success', text: 'Parcelas geradas com sucesso!'});
				$scope.findByContrato(contrato);
				$scope.findByFilter();
			}, 
			function(response) {
				if ((response.status == 409) || (response.status == 400)) {
	                $scope.errors = response.data;
	                if (response.data.error) {
	                	noty({layout: 'topRight', type: 'error', text: response.data.error});
	                }
	            } else {
	                $scope.errorMessages = [ 'Unknown  server error' ];
	            }
			});
	};
	
	$scope.findByContrato = function(contrato){
		$scope.currentContrato = contrato;
		$scope.filter2.contratoId = contrato.id;
		$scope.findByContratoService();
	};
	
	$scope.findByContratoService = function(){
		PagamentoService.findByContrato($scope.filter2, function(response) {
			$scope.pagamentos = response.data;
			$scope.filter2.count = response.count;
		});
	};
	
	$scope.doPayment = function(pagamento){
		PagamentoService.doPayment({'id': pagamento.id}, function(response) {
			$scope.findByContratoService();
			$scope.findByFilter();
		});
	};
	
	$scope.dismissPayment = function(pagamento){
		PagamentoService.dismissPayment({'id': pagamento.id}, function(response) {
			$scope.findByContratoService();
			$scope.findByFilter();
		});
	};
	
	$scope.confirmDelete = function(contrato) {
		var title = 'Atenção';
		var msg = 'Você realmente deseja excluir as parcelas do contrato ' + contrato.id + '?';
		var btns = [ {
			result : 'delete',
			label : 'Excluir',
			cssClass : 'btn-danger'
		}, {
			result : 'cancel',
			label : 'Cancelar'
		} ];

		$dialog.messageBox(title, msg, btns).open().then(function(result) {
			if ('delete' == result) {
				PagamentoService.removeByContrato({
					id : contrato.id
				}, // success
				function(data, headers) {
					// send local message
					$scope.findByFilter();
					$scope.findByContrato(contrato);
					noty({layout: 'topRight', type: 'success', text: 'Parcelas removidas com sucesso.'});
				},
				function(response) {
					if ((response.status == 409) || (response.status == 400)) {
		                $scope.errors = response.data;
		                if (response.data.error) {
		                	noty({layout: 'topRight', type: 'error', text: response.data.error});
		                }
		            } else {
		                $scope.errorMessages = [ 'Unknown  server error' ];
		            }
				});
			}
		});

	};
	
	
	$scope.selectAll = function(check) {
		for (var i = 0; i < $scope.pagamentos.length; i++) {
			$scope.pagamentos[i].isSelected = document.getElementById('headerCheckbox').checked;
			if (document.getElementById('headerCheckbox').checked == false) {
				$scope.pagamentosSelected = 0;
			} else {
				$scope.pagamentosSelected = $scope.pagamentos.length;
			}
		}
	};
	
	$scope.checkSelection = function(isSelected) {
		if (isSelected == true) {
			$scope.pagamentosSelected += 1;
		} else {
			$scope.pagamentosSelected -= 1;
		}
		
		document.getElementById('headerCheckbox').checked = true;
		for (var i = 0; i < $scope.pagamentos.length; i++) {
			if ($scope.pagamentos[i].isSelected == false) {
				document.getElementById('headerCheckbox').checked = false;
				break;
			}
		}
	};
	
	$scope.report = function(){
		var str = "";
		
		for (var i = 0; i < $scope.pagamentos.length; i++) {
			if ($scope.pagamentos[i].isSelected == true) {
				if(str == "")
					str += $scope.pagamentos[i].id;
				else
					str += "," + $scope.pagamentos[i].id;
			}
		}
		
		$window.open("/Transporte/servlet/pagamento?contratoId=" + $scope.currentContrato.id + "&pagamentoId=" + str, "_blank");
		
	};
	
}