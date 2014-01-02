function PerfilCtrl($scope, $http, $dialog, $window, $timeout, UserService, TransportadorService, CEPService, AssociacaoService) {
	
	/**
	 * VAR INITIALIZATION
	 */
	
	// errors 
	$scope.successMessages = '';
    $scope.errorMessages = '';
    $scope.errors = {};
    $scope.errorsTransportador = {};
	
	$scope.loggedUser = new Object();
	$scope.userTransportadora = new Object();
	$scope.userTransportadora.logotipo = '';
	$scope.userTransportadora.endereco = new Object();
	$scope.userTransportadora.endereco.endereco = '';
	$scope.userTransportadora.endereco.numero = '';
	$scope.userTransportadora.endereco.bairro = '';
	$scope.userTransportadora.endereco.cidade = '';
	$scope.userTransportadora.endereco.uf = '';
	$scope.userTransportadora.endereco.cep = '';
	$scope.userTransportadora.endereco.latitude = '';
	$scope.userTransportadora.endereco.longitude = '';
	
	$scope.addressChanged = false;
	
	$scope.addressEdit = false;
	
	/**
	 * WATCHS
	 */
	
	$scope.$watch('userTransportadora.endereco.endereco + userTransportadora.endereco.numero + ' +
			'userTransportadora.endereco.bairro + userTransportadora.endereco.cidade + ' +
			'userTransportadora.endereco.uf + userTransportadora.endereco.cep + ' +
			'userTransportadora.endereco.pais', function(newValue, oldValue) {
		if (newValue != oldValue && !$scope.addressEdit) {
			$scope.addressChanged = true;
		}else{
			$scope.addressChanged = false;
		}
		
		$scope.addressEdit = false;
	});
	
	
	/**
	 * FUNCTIONS
	 */
	$scope.cgcInit = function(){
		if($scope.userTransportadora != null && $scope.userTransportadora.cgc != null){
			if($scope.userTransportadora.cgc.length <= 11){		
				$scope.maskCGC = "999.999.999-99";
				$scope.cgcType = "CPF";
			}else{
				$scope.maskCGC = "99.999.999/9999-99";
				$scope.cgcType = "CNPJ";
			}
		}else{
			$scope.maskCGC = "999.999.999-99";
			$scope.cgcType = "CPF";
		}
		
	};
	
	$scope.typeCGCChange = function(){
		$scope.userTransportadora.cgc = "";

		if($scope.cgcType == "CNPJ"){
			$scope.maskCGC = "999.999.999-99";
			$scope.cgcType = "CPF";
		}else{
			$scope.maskCGC = "99.999.999/9999-99";
			$scope.cgcType = "CNPJ";
		}
	};
	
	$scope.cepBlur = function(cep){
		CEPService.getAddressByCEP({'cep': cep}, 
			function(result) {
				$scope.userTransportadora.endereco.endereco = result.tipoLogradouro + " " + result.logradouro;
				$scope.userTransportadora.endereco.bairro = result.bairro;
				$scope.userTransportadora.endereco.cidade = result.cidade;
				$scope.userTransportadora.endereco.uf = result.uf;
				$scope.userTransportadora.endereco.pais = "Brasil";
				$('#numeroInput').focus();
			}
		);
	};
	
	$scope.validateMap = function() {
		$scope.$broadcast('validateMap'); 
	};
	
	$scope.validateMapOK = function(){
		$scope.addressChanged = false;
	};
	
	$scope.update = function() {
		UserService.update($scope.loggedUser, function(data, headers) {

			if ($scope.userTransportadora.id == undefined) {
				TransportadorService.save($scope.userTransportadora, function(data,headers) {
					$('#perfilModal').modal('hide');
				}, function(response) {
					if ((response.status == 409) || (response.status == 400)) {
						$scope.errorsTransportador = response.data;
		                if (response.data.error) {
		                	noty({layout: 'topRight', type: 'error', text: response.data.error});
		                }
		            } else {
		                $scope.errorMessages = [ 'Unknown  server error' ];
		            } 
				});
			} else {
				TransportadorService.update($scope.userTransportadora, function(data,headers) {
					$('#perfilModal').modal('hide');
				}, function(response) {
					if ((response.status == 409) || (response.status == 400)) {
						$scope.errorsTransportador = response.data;
		                if (response.data.error) {
		                	noty({layout: 'topRight', type: 'error', text: response.data.error});
		                }
		            } else {
		                $scope.errorMessages = [ 'Unknown  server error' ];
		            } 
				});

			noty({layout: 'topRight', type: 'success', text: 'Registro salvo com sucesso!'});
			}
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
	};
	
	/**
	 * FINDS
	 */
	
	$scope.findLoggedUser = function() {
		$scope.errors = {};
	    $scope.errorsTransportador = {};
	    
		UserService.findLoggedUser([], function(data, headers) {
				$scope.loggedUser = data;
				
				TransportadorService.findByLoggedUser([], function(data,headers) {
					$scope.addressEdit = true;
					
					$scope.userTransportadora = data;
					
					var imgtag = document.getElementById("imgPerfil");
					if($scope.userTransportadora.logotipo != undefined)
						imgtag.src = $scope.userTransportadora.logotipo;
					
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
	};
	
    //listen for the file selected event
    $scope.$on("fileSelected", function (event, args) {
        $scope.$apply(function () {            
            //add the file object to the scope's files collection
			var selectedFile = args.file;
			var reader = new FileReader();
			
			var imgtag = document.getElementById("imgPerfil");
			imgtag.title = selectedFile.name;
			
			reader.onload = function(event) {
				$scope.userTransportadora.logotipo = event.target.result;
				imgtag.src = event.target.result;
			};
			
			reader.readAsDataURL(selectedFile);
        });
    });
    
  //ZOOM Associacao
	$scope.associacaoFKOpt = {
		placeholder: "Selecione um registro",
		minimumInputLength: 1,
		allowClear:true,
		ajax: {
            url: "/Transporte/webservices/rest/associacao/zoom",
            dataType: 'jsonp',
            quietMillis: 200,
            data: function (term, page) { // page is the one-based page number tracked by Select2
                return {
                    nomeAbrev: term, //search term
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
        formatResult: function (item) { return item.nomeAbrev; },
        formatSelection: function (item) { return item.nomeAbrev; },
        escapeMarkup: function (m) { return m; }
        
    };
	
	/**
	 * LISTS
	 */
	
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
	
}