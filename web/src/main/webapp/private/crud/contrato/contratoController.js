function ContratoCtrl($scope, $http, $dialog, $window, ContratoService) {
	
	// errors 
	$scope.successMessages = '';
    $scope.errorMessages = '';
    $scope.errors = {};
	
    // filter
	$scope.filter = new Object();
	$scope.filter.ano =  moment(new Date()).year();
	$scope.filter.count = 0;
	$scope.filter.startRow = 1;
	$scope.filter.pageSize = 5;
	
	$scope.contratos = [];
	$scope.contratosSelected = 0;
	$scope.qtParcelas = 0;
	
	$scope.numPages = function() {
		return Math.ceil($scope.filter.count / $scope.filter.pageSize);
	};
	
	//ZOOM Cliente
	$scope.clienteFKOpt = {
		placeholder: "Selecione o cliente",
		minimumInputLength: 1,
		allowClear:true,
		ajax: {
            url: "/Transporte/webservices/rest/cliente/zoom",
            dataType: 'jsonp',
            quietMillis: 200,
            data: function (term, page) { // page is the one-based page number tracked by Select2
                return {
                    nome: term, //search term
                    count: 0, // page size
                    startRow: page, // page number
                    pageSize: 10
                };
            },
            results: function (data, page) {
                var more = (page * 10) < data.count; // whether or not there are more results available

                // notice we return the value of more so Select2 knows if more results can be loaded
                return {results: data.data, more: more};
            }
        },
        formatResult: function (item) { return item.nome; },
        formatSelection: function (item) { return item.nome; },
        escapeMarkup: function (m) { return m; }
        
    };
	
	$scope.findById = function(contrato) {
		ContratoService.findById({
			id : contrato.id
		}, function(response) {
			$scope.newContrato = response;
			$scope.calculaNumParcelas();
		});
	};
	
	$scope.findByFilter = function() {
		$scope.contratosSelected = 0;
		document.getElementById('headerCheckbox').checked = false;
		
		ContratoService.findByFilter($scope.filter, function(response) {
			$scope.contratos = response.data;
			$scope.filter.count = response.count;
		});
	};
	
	$scope.$watch('filter.startRow + filter.pageSize', function() {
		$scope.findByFilter();
	});

	$scope.confirmDelete = function(contrato) {
		var title = 'Atencao';
		var msg = 'Voce realmente deseja excluir o contrato do cliente ' + contrato.cliente.nome + '?';
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
				ContratoService.remove({
					id : contrato.id
				}, // success
				function(data, headers) {
					// send local message
					$scope.findByFilter();
					noty({layout: 'topRight', type: 'success', text: 'Contrato removido com sucesso.'});
				},
				// error
				function(response) {
					noty({layout: 'topRight', type: 'error', text: response.data.error});
				});
			}
		});

	};
	
	$scope.save = function() {
		
		if($scope.isEditing == false) {
			ContratoService.save($scope.newContrato, function(data, headers) {
				$scope.findByFilter();
				$scope.close();
				noty({layout: 'topRight', type: 'success', text: 'Contrato salvo com sucesso.'});
			}, function(response) {
				if ((response.status == 409) || (response.status == 400)) {
	                $scope.errors = response.data;
	                if (response.data.error) {
	                	noty({layout: 'topRight', type: 'error', text: response.data.error});
	                }
	            } else {
	                $scope.errorMessages = [ 'Unknown  server error' ];
	            }
			});
		} else {
			ContratoService.update($scope.newContrato, function(data, headers) {
				$scope.findByFilter();
				$scope.close();
				noty({layout: 'topRight', type: 'success', text: 'Contrato atualizado com sucesso.'});
			}, function(response) {
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

	};
	
	$scope.edit = function(contrato) {
		
		$scope.reset();
		$scope.findById(contrato);
		$scope.isEditing = true;
	};
	
	$scope.report = function(contrato){
		
		$window.open("/Transporte/servlet/contrato?contratoId=" + contrato.id,"_blank");
		
	};
	
	// Define a reset function, that clears the prototype newUser object, and
	// consequently, the form
	$scope.reset = function() {
		// clear input fields
		$scope.newContrato = {};
		$scope.newContrato.dtIni = '';
		$scope.newContrato.dtFim = '';
		$scope.isEditing = false;
		// clear errors fields
		$scope.successMessages = '';
	    $scope.errorMessages = '';
	    $scope.errors = {};
	};
	
	// close modal
	$scope.close = function(result){
		$('#modalContrato').modal('hide');
		
	};
	
	$scope.selectAll = function(check) {
		for (var i = 0; i < $scope.contratos.length; i++) {
			$scope.contratos[i].isSelected = document.getElementById('headerCheckbox').checked;
			if (document.getElementById('headerCheckbox').checked == false) {
				$scope.contratosSelected = 0;
			} else {
				$scope.contratosSelected = $scope.contratos.length;
			}
		}
	};
	
	$scope.checkSelection = function(isSelected) {
		if (isSelected == true) {
			$scope.contratosSelected += 1;
		} else {
			$scope.contratosSelected -= 1;
		}
		
		document.getElementById('headerCheckbox').checked = true;
		for (var i = 0; i < $scope.contratos.length; i++) {
			if ($scope.contratos[i].isSelected == false) {
				document.getElementById('headerCheckbox').checked = false;
				break;
			}
		}
		
	};
	
	$scope.calculaNumParcelas = function() {
		if ($scope.newContrato.dtIni != null && $scope.newContrato.dtFim != null && $scope.newContrato.dtIni != "" && $scope.newContrato.dtFim != "") {
			var finalDateMonth = moment($scope.newContrato.dtFim).month();
			var initialDateMonth = moment($scope.newContrato.dtIni).month();
			
			$scope.qtParcelas = (finalDateMonth - initialDateMonth) + 1;
		}
	};
	
	$scope.selected = undefined;

	// Initialize newUser here to prevent Angular from sending a request
	// without a proper Content-Type.
	$scope.reset();
	
	$scope.orderBy = "id";
}