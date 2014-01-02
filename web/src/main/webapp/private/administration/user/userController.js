function UserCtrl($scope, $http, $dialog, $window, UserService) {
	
	// errors 
	$scope.successMessages = '';
    $scope.errorMessages = '';
    $scope.errors = {};
	
    // filter
	$scope.filter = new Object();
	$scope.filter.name = '';
	$scope.filter.count = 0;
	$scope.filter.startRow = 1;
	$scope.filter.pageSize = 5;
	
	$scope.numPages = function() {
		return Math.ceil($scope.filter.count / $scope.filter.pageSize);
	};
	
	$scope.findById = function(user) {
		UserService.findById({
			id : user.id
		}, function(response) {
			$scope.newUser = response;
		});
	};
	
	$scope.findByFilter = function() {
		UserService.findByFilter($scope.filter, function(response) {
			$scope.users = response.data;
			$scope.filter.count = response.count;
		});
	};
	
	$scope.$watch('filter.startRow + filter.pageSize', function() {
		$scope.findByFilter();
	});

	$scope.confirmDelete = function(user) {
		var title = 'Atencao';
		var msg = 'Voce realmente deseja excluir o usuario ' + user.name + '?';
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
				UserService.remove({
					id : user.id
				}, // success
				function(data, headers) {
					// send local message
					$scope.findByFilter();
					noty({layout: 'topRight', type: 'success', text: 'Usuário removido com sucesso.'});
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
			UserService.save($scope.newUser, function(data, headers) {
				$scope.findByFilter();
				$scope.close(); 
				noty({layout: 'topRight', type: 'success', text: 'Usuário ' + $scope.newUser.name + ' salvo com sucesso.'});
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
			UserService.update($scope.newUser, function(data, headers) {
				$scope.findByFilter();
				$scope.close();
				noty({layout: 'topRight', type: 'success', text: 'Usuário ' + $scope.newUser.name + ' atualizado com sucesso.'});
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

	$scope.edit = function(user) {
		$scope.reset();
		$scope.findById(user);
		$scope.isEditing = true;
	};
	
	// Define a reset function, that clears the prototype newUser object, and
	// consequently, the form
	$scope.reset = function() {
		// clear input fields
		$scope.newUser = {};
		$scope.isEditing = false;
		// clear errors fields
		$scope.successMessages = '';
	    $scope.errorMessages = '';
	    $scope.errors = {};
	};
	
	// close modal
	$scope.close = function(result){
		$('#modalUsuario').modal('hide');
	};
	
	$scope.selected = undefined;

	// Initialize newUser here to prevent Angular from sending a request
	// without a proper Content-Type.
	$scope.reset();
	
	$scope.orderBy = "id";
	
	//ZOOM ROLES
	$scope.rolesFKOpt = {
		placeholder: "Selecione as permissões do usuário",
		minimumInputLength: 1,
		allowClear:true,
		multiple: true,
		ajax: {
            url: "/Transporte/webservices/rest/role/findByFilter",
            dataType: 'jsonp',
            quietMillis: 200,
            data: function (term, page) { // page is the one-based page number tracked by Select2
                return {
                    role: term, //search term
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
        formatResult: function (item) { return item.role; },
        formatSelection: function (item) { return item.role; },
        escapeMarkup: function (m) { return m; }
    };
	
	//ZOOM PLANOS
	$scope.plansFKOpt = {
		placeholder: "Selecione o plano do usuário",
		minimumInputLength: 1,
		allowClear:true,
		multiple: false,
		ajax: {
            url: "/Transporte/webservices/rest/plan/findByFilter",
            dataType: 'jsonp',
            quietMillis: 200,
            data: function (term, page) { // page is the one-based page number tracked by Select2
                return {
                    name: term, //search term
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
        formatResult: function (item) { return item.name; },
        formatSelection: function (item) { return item.name; },
        escapeMarkup: function (m) { return m; }
    };
}