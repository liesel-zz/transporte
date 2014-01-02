function HomeCtrl($scope, $http, $dialog, $window, UserService) {
	
	$scope.save = function() {
		alert("teste " + $scope.isEditing);
		if($scope.isEditing == false) {
			UserService.save($scope.newUser, function(data, headers) {
				// var message = 'Usuario '+ data.name + ' foi salvo com sucesso!';
				// var btns = [{result:'OK', label: 'Ok!', cssClass:
				// 'btn-primary'}];
				// $dialog.messageBox('Usuário Criado', message, btns).open();
				$scope.findByFilter();
			}, function(response) {
				$window.alert("RUN TO THE HILLS");
			});
		} else {
			//alert($scope.newUser.birthDay);
			UserService.update($scope.newUser, function(data, headers) {
				// var message = 'Usuario '+ data.name + ' foi salvo com sucesso!';
				// var btns = [{result:'OK', label: 'Ok!', cssClass:
				// 'btn-primary'}];
				// $dialog.messageBox('Usuário Criado', message, btns).open();
				$scope.findByFilter();
			}, function(response) {
				$window.alert("RUN TO THE HILLS");
			});
		}

	};

	// Define a reset function, that clears the prototype newUser object, and
	// consequently, the form
	$scope.reset = function() {
		// clear input fields
		$scope.newUser = {};
		$scope.isEditing = false;
	};
	
	$scope.selected = undefined;

	// Initialize newUser here to prevent Angular from sending a request
	// without a proper Content-Type.
	$scope.reset();

}