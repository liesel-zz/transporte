function EstudanteCtrl($scope, $http, $dialog, $window, EstudanteService, CEPService) {
	
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
		EstudanteService.findByFilter($scope.filter, function(response) {
			$scope.items = response.data;
			$scope.filter.count = response.count;
		});
	};
	
	$scope.$watch('filter.startRow + filter.pageSize', function() {
		$scope.findByFilter();
	});
	
	$scope.$watch('currentItem.estudante.cliente', function() {
		if ($scope.currentItem.estudante.cliente != undefined && $scope.currentItem.estudante.cliente != null && $scope.isEditing == false){
			if ($scope.currentItem.enderecos == null || $scope.currentItem.enderecos == undefined)
				$scope.currentItem.enderecos = new Array();
			$scope.currentItem.enderecos.push({descricao: "Casa dos Pais", endereco: $scope.currentItem.estudante.cliente.endereco});
		}
	});
	
	$scope.$watch('currentEndereco.endereco.endereco + currentEndereco.endereco.numero + ' +
			'currentEndereco.endereco.bairro + currentEndereco.endereco.cidade + ' +
			'currentEndereco.endereco.uf + currentEndereco.endereco.cep + ' +
			'currentEndereco.endereco.pais', function(newValue, oldValue) {
		if (newValue != oldValue && !$scope.addressEdit) {
			$scope.addressChanged = true;
		}else{
			$scope.addressChanged = false;
		}
		
		$scope.addressEdit = false;
	});
	
	$scope.confirmDelete = function(item) {
		var title = 'Atencao';
		var msg = 'Você realmente deseja excluir o estudante ' + item.nome + '?';
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
				EstudanteService.remove({
					id : item.id
				}, // success
				function(data, headers) {
					// send local message
					$scope.findByFilter();
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
			EstudanteService.save($scope.currentItem, function(data, headers) {
				$scope.findByFilter();
			}, function(response) {
				noty({layout: 'topRight', type: 'error', text: response.data.error});
			});
		} else {
			EstudanteService.update($scope.currentItem, function(data, headers) {
				$scope.findByFilter();
			}, function(response) {
				noty({layout: 'topRight', type: 'error', text: response.data.error});
			});
		}

	};

	$scope.edit = function(item) {
		//$scope.currentItem.estudante = JSON.parse(JSON.stringify(item));
		EstudanteService.findById({'id': item.id}, 
								function(response) {
									$scope.currentItem.estudante = response.estudante;
									$scope.currentItem.enderecos = response.enderecos;
								}, function(response) {
									noty({layout: 'topRight', type: 'error', text: response.data.error});
								});
		$scope.isEditing = true;
	};
	
	$scope.editEndereco = function(end) {
		$scope.currentEndereco = JSON.parse(JSON.stringify(end));
		$scope.isEditingEnd = true;
		$scope.addressEdit = true;
	};
	
	$scope.deleteEndereco = function(end){
		$scope.currentItem.enderecos.splice($scope.currentItem.enderecos.indexOf(end),1);
	};
	
	$scope.saveEndereco = function() {
		if($scope.isEditingEnd){
			for(var i = 0; i < $scope.currentItem.enderecos.length; i++){
				$scope.currentItem.enderecos[i] = $scope.currentEndereco;
			}
		}else{
			$scope.currentItem.enderecos.push($scope.currentEndereco);
		}
	};
	
	// Define a reset function, that clears the prototype newUser object, and
	// consequently, the form
	$scope.reset = function() {
		// clear input fields
		$scope.currentItem = new Object();
		$scope.currentItem.estudante = new Object();
		$scope.currentItem.enderecos = [];
		$scope.isEditing = false;
	};
	
	$scope.resetEnd = function() {
		// clear input fields
		$scope.currentEndereco = new Object();
		$scope.isEditingEnd = false;
		
		$scope.addressChanged = true;
	};
			
	// Initialize newUser here to prevent Angular from sending a request
	// without a proper Content-Type.
	$scope.reset();
	$scope.resetEnd();
		
	$scope.validateMap = function() {
		$scope.$broadcast('validateMap'); 
	};
	
	$scope.validateMapOK = function(){
		$scope.addressChanged = false;
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
	
	$scope.cepBlur = function(cep){
		CEPService.getAddressByCEP({'cep': cep}, 
			function(result) {
				$scope.currentEndereco.endereco.endereco = result.tipoLogradouro + " " + result.logradouro;
				$scope.currentEndereco.endereco.bairro = result.bairro;
				$scope.currentEndereco.endereco.cidade = result.cidade;
				$scope.currentEndereco.endereco.uf = result.uf;
				$scope.currentEndereco.endereco.pais = "Brasil";
				$('#numeroInput').focus();
			}
		);
	};
	
	//ZOOM Cliente
	$scope.clienteFKOpt = {
		placeholder: "Selecione o responsável pelo estudante",
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
	
	//ZOOM Instituicao
	$scope.instituicaoFKOpt = {
		placeholder: "Selecione a(s) instituição(ões)",
		minimumInputLength: 1,
		allowClear:true,
		multiple: true,
		ajax: {
            url: "/Transporte/webservices/rest/instituicao/zoom",
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
        formatSelection: function (item) { return item.nomeAbrev; },
        escapeMarkup: function (m) { return m; }
        
    };
}