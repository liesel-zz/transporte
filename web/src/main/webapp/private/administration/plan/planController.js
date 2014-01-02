function PlanCtrl($scope, $http, $dialog, $window, PlanService) {
	
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
	
	$scope.findById = function(plan) {
		PlanService.findById({
			id : plan.id
		}, function(response) {
			$scope.newPlan = response;
		});
	}
	
	$scope.findByFilter = function() {
		PlanService.findByFilter($scope.filter, function(response) {
			$scope.plans = response.data;
			$scope.filter.count = response.count;
		});
	}
	
	$scope.$watch('filter.startRow + filter.pageSize', function() {
		$scope.findByFilter();
	});

	$scope.confirmDelete = function(plan) {
		var title = 'Atencao';
		var msg = 'Voce realmente deseja excluir o plano ' + plan.name + '?';
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
				PlanService.remove({
					id : plan.id
				}, // success
				function(data, headers) {
					// send local message
					$scope.findByFilter();
					noty({layout: 'topRight', type: 'success', text: 'Plano removido com sucesso.'});
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
			PlanService.save($scope.newPlan, function(data, headers) {
				$scope.findByFilter();
				$scope.close();
				noty({layout: 'topRight', type: 'success', text: 'Plano ' + $scope.newPlan.name + ' salvo com sucesso.'});
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
			PlanService.update($scope.newPlan, function(data, headers) {
				$scope.findByFilter();
				$scope.close();
				noty({layout: 'topRight', type: 'success', text: 'Plano ' + $scope.newPlan.name + ' atualizado com sucesso.'});
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
	
	$scope.edit = function(plan) {
		$scope.reset();
		$scope.findById(plan);
		$scope.isEditing = true;
	};
	
	// Define a reset function, that clears the prototype newUser object, and
	// consequently, the form
	$scope.reset = function() {
		// clear input fields
		$scope.newPlan = {};
		$scope.isEditing = false;
		// clear errors fields
		$scope.successMessages = '';
	    $scope.errorMessages = '';
	    $scope.errors = {};
	};
	
	// close modal
	$scope.close = function(result){
		$('#modalPlan').modal('hide')
	};
	
	$scope.selected = undefined;

	// Initialize newUser here to prevent Angular from sending a request
	// without a proper Content-Type.
	$scope.reset();
	
	$scope.orderBy = "id";

}