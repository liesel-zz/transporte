function ResetarSenhaCtrl($scope, $location, $http, $dialog, $window, $timeout,
		UserService) {

	$scope.$location = $location;

	// errors
	$scope.successMessages = '';
	$scope.errorMessages = '';
	$scope.errors = {};

	$scope.resetPassword = function() {
		UserService.resetPassword($scope.newPassword, function(data, headers) {
			noty({
				layout : 'topRight',
				type : 'success',
				text : 'Senha alterada com sucesso!'
			});
			$timeout(function() {
				location.pathname = '/Transporte';
			},2000);
		}, function(response) {
			if ((response.status == 409) || (response.status == 400)) {
				$scope.errors = response.data;
				if (response.data.error) {
					noty({
						layout : 'topRight',
						type : 'error',
						text : response.data.error
					});
				}
			} else {
				$scope.errorMessages = [ 'Unknown  server error' ];
			}
		});
	};
	
	$scope.getURLParam = function(param) {
	    var result =  location.search.match(new RegExp("(\\?|&)" + param + "(\\[\\])?=([^&]*)"));

	    return result ? result[3] : "";
	};
	
	$scope.reset = function() {
		// clear errors fields
		$scope.successMessages = '';
		$scope.errorMessages = '';
		$scope.errors = {};

		// clear input fields
		$scope.newPassword = {};
		$scope.newPassword.uuid = $scope.getURLParam("key");
		$scope.newPassword.newPassword = '';
		$scope.newPasswordRepeat = '';
		
	};

	// Initialize newUser here to prevent Angular from sending a request
	// without a proper Content-Type.
	$scope.reset();

}