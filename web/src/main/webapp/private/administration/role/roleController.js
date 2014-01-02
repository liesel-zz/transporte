function RoleCtrl($scope, $http, $dialog, $window, RoleService) {
	
	// errors 
	$scope.successMessages = '';
    $scope.errorMessages = '';
    $scope.errors = {};
	
	$scope.filter = new Object();
	
	$scope.filter.role = '';
	
	$scope.filter.count = 0;
	$scope.filter.startRow = 1;
	$scope.filter.pageSize = 5;
	
	$scope.numPages = function() {
		return Math.ceil($scope.filter.count / $scope.filter.pageSize);
	};

	$scope.findByFilter = function() {
		RoleService.findByFilter($scope.filter, function(response) {
			$scope.roles = response.data;
			$scope.filter.count = response.count;
		});
	}

	$scope.$watch('filter.startRow + filter.pageSize', function() {
		$scope.findByFilter();
	});

	$scope.confirmDelete = function(role) {
		var title = 'Atencao';
		var msg = 'Voce realmente deseja excluir a regra ' + role.role + '?';
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
				RoleService.remove({
					id : role.id
				}, // success
				function(data, headers) {
					// send local message
					$scope.findByFilter();
				},
				// error
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

	$scope.save = function() {
		if ($scope.isEditing == false) {
			RoleService.save($scope.newRole, function(data, headers) {
				// var message = 'Usuario '+ data.name + ' foi salvo com
				// sucesso!';
				// var btns = [{result:'OK', label: 'Ok!', cssClass:
				// 'btn-primary'}];
				// $dialog.messageBox('Usuário Criado', message, btns).open();
				$scope.findByFilter();

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
			RoleService.update($scope.newRole, function(data, headers) {
				// var message = 'Usuario '+ data.name + ' foi salvo com
				// sucesso!';
				// var btns = [{result:'OK', label: 'Ok!', cssClass:
				// 'btn-primary'}];
				// $dialog.messageBox('Usuário Criado', message, btns).open();
				$scope.findByFilter();

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

	$scope.edit = function(role) {
		$scope.newRole = JSON.parse(JSON.stringify(role));
		$scope.isEditing = true;
	};

	// Define a reset function, that clears the prototype newRole object, and
	// consequently, the form
	$scope.reset = function() {
		// clear input fields
		$scope.successMessages = '';
	    $scope.errorMessages = '';
	    $scope.errors = {};
	    
		$scope.newRole = {};
		$scope.isEditing = false;
	};
	
	// close modal
	$scope.close = function(result){
		$('#modalRole').modal('hide');
	};

	$scope.selected = undefined;

	// Initialize newRole here to prevent Angular from sending a request
	// without a proper Content-Type.
	$scope.reset();

	$scope.orderBy = "role";

}