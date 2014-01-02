function InstituicaoCtrl($scope, $http, $dialog, $window, InstituicaoService, CEPService) {
	
	// errors 
	$scope.successMessages = '';
    $scope.errorMessages = '';
    $scope.errors = {};
	
	$scope.orderBy = "id";
		
	$scope.filter = new Object();
	$scope.filter.nome = "";
	$scope.filter.count = 0;
	$scope.filter.startRow = 1;
	$scope.filter.pageSize = 5;
	
	$scope.addressChanged = false;
	
	$scope.addressEdit = false;
	
	
	$scope.numPages = function() {
		return Math.ceil($scope.filter.count / $scope.filter.pageSize);
	};
	
	$scope.findByFilter = function() {
		InstituicaoService.findByFilter($scope.filter, function(response) {
			$scope.items = response.data;
			$scope.filter.count = response.count;
		});
	};
	
	$scope.$watch('currentItem.endereco.endereco + currentItem.endereco.numero + ' +
			'currentItem.endereco.bairro + currentItem.endereco.cidade + ' +
			'currentItem.endereco.uf + currentItem.endereco.cep + ' +
			'currentItem.endereco.pais', function(newValue, oldValue) {
		if (newValue != oldValue && !$scope.addressEdit) {
			$scope.addressChanged = true;
		}else{
			$scope.addressChanged = false;
		}
		
		$scope.addressEdit = false;
	});
	
	$scope.$watch('filter.startRow + filter.pageSize', function() {
		$scope.findByFilter();
	});
	
	$scope.confirmDelete = function(item) {
		var title = 'Atencao';
		var msg = 'Você realmente deseja excluir a instituição ' + item.nome + '?';
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
				InstituicaoService.remove({
					id : item.id
				}, // success
				function(data, headers) {
					// send local message
					$scope.findByFilter();
					noty({layout: 'topRight', type: 'success', text: 'Instituição removida com sucesso.'});
				},
				// error
				function(response) {
					if (response.data.error) {
	                	noty({layout: 'topRight', type: 'error', text: response.data.error});
	                }
				});
			}
		});

	};
	
	$scope.save = function() {
		if($scope.isEditing == false) {
			InstituicaoService.save($scope.currentItem, function(data, headers) {
				$scope.findByFilter();
				noty({layout: 'topRight', type: 'success', text: 'Instituição ' + $scope.currentItem.nomeAbrev + ' salva com sucesso!'});
				$scope.reset();
				$scope.close(); 
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
			InstituicaoService.update($scope.currentItem, function(data, headers) {
				$scope.findByFilter();
				noty({layout: 'topRight', type: 'success', text: 'Instituição ' + $scope.currentItem.nomeAbrev + ' salva com sucesso!'});
				$scope.close();
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

	$scope.edit = function(item) {
		$scope.reset();
		$scope.findById(item.id);
		$scope.isEditing = true;
	};
	
	$scope.findById = function(id) {
		InstituicaoService.findById({'id': id}, function(response) {
			$scope.addressEdit = true;
			$scope.currentItem = response;
		});
	};
	
	// Define a reset function, that clears the prototype newUser object, and
	// consequently, the form
	$scope.reset = function() {
		// clear input fields
		$scope.currentItem = new Object();
		$scope.currentItem.endereco = new Object();
		$scope.currentItem.endereco.endereco = '';
		$scope.currentItem.endereco.numero = '';
		$scope.currentItem.endereco.bairro = '';
		$scope.currentItem.endereco.cidade = '';
		$scope.currentItem.endereco.uf = '';
		$scope.currentItem.endereco.cep = '';
		$scope.currentItem.endereco.latitude = '';
		$scope.currentItem.endereco.longitude = '';
		$scope.isEditing = false;
		
		$scope.addressChanged = true;
		
		$scope.successMessages = '';
	    $scope.errorMessages = '';
	    $scope.errors = {};
	};
			
	// Initialize newUser here to prevent Angular from sending a request
	// without a proper Content-Type.
	$scope.reset();
	
	$scope.validateMap = function() {
		$scope.$broadcast('validateMap'); 
	};
	
	$scope.validateMapOK = function(){
		$scope.addressChanged = false;
	};
	
	// close modal
	$scope.close = function(result){
		$('#modalForm').modal('hide');
	};
	
	$scope.ufList = [{id:'AC', name:'Acre'},
					{id:'AL', name:'Alagoas'},
					{id:'AP', name:'Amapá'},
					{id:'AM', name:'Amazonas'},
					{id:'BA', name:'Bahia '},
					{id:'CE', name:'Ceará'},
					{id:'DF', name:'Distrito Federal '},
					{id:'ES', name:'Espírito Santo'},
					{id:'GO', name:'Goiás'},
					{id:'MA', name:'Maranhão'},
					{id:'MT', name:'Mato Grosso'},
					{id:'MS', name:'Mato Grosso do Sul'},
					{id:'MG', name:'Minas Gerais'},
					{id:'PA', name:'Pará'},
					{id:'PB', name:'Paraíba'},
					{id:'PR', name:'Paraná'},
					{id:'PE', name:'Pernambuco'},
					{id:'PI', name:'Piauí'},
					{id:'RJ', name:'Rio de Janeiro'},
					{id:'RN', name:'Rio Grande do Norte'},
					{id:'RS', name:'Rio Grande do Sul'},
					{id:'RO', name:'Rondônia'},
					{id:'RR', name:'Roraima'},
					{id:'SC', name:'Santa Catarina'},
					{id:'SP', name:'São Paulo'},
					{id:'SE', name:'Sergipe'},
					{id:'TO', name:'Tocantins'}];
	
	$scope.tipoList = [{id:'Escola', name:'Escola'},
	                   {id:'Jardim', name:'Jardim'},
	                   {id:'Creche', name:'Creche'}];
	
	$scope.cepBlur = function(cep){
		CEPService.getAddressByCEP({'cep': cep}, 
			function(result) {
				$scope.currentItem.endereco.endereco = result.tipoLogradouro + " " + result.logradouro;
				$scope.currentItem.endereco.bairro = result.bairro;
				$scope.currentItem.endereco.cidade = result.cidade;
				$scope.currentItem.endereco.uf = result.uf;
				$scope.currentItem.endereco.pais = "Brasil";
				$('#numeroInput').focus();
			}
		);
		
	};
	
}