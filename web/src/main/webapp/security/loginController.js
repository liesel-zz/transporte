function LoginCtrl($scope, $location, $http, $dialog, $window, $timeout,
		UserService) {

	// errors
	$scope.successMessages = '';
	$scope.errorMessages = '';
	$scope.errors = {};

	$scope.lostPassword = function() {

		if ($scope.j_username == null || $scope.j_username == '') {
			noty({
				layout : 'topRight',
				type : 'warning',
				text : 'Para esta função é necessário o preenchimento do campo [E-mail]'
			});
		} else {
			UserService
					.lostPassword(
							$scope.j_username,
							function(data, headers) {
								noty({
									layout : 'topRight',
									type : 'success',
									text : 'Foi enviado um e-mail para a recuperação de senha, verifique sua caixa de entrada'
								});
							},
							function(response) {
								if ((response.status == 409)
										|| (response.status == 400)) {
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
		}
	};

	$scope.save = function() {
		UserService.register($scope.newUser, function(data, headers) {
			$scope.close();
			noty({
				layout : 'topRight',
				type : 'success',
				text : 'Usuário ' + $scope.newUser.name + ' cadastrado com sucesso, cheque seu e-mail para a confirmação do cadastro'
			});
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
	
	$scope.reset = function() {
		// clear input fields
		$scope.newUser = {};

		$scope.successMessages = '';
	    $scope.errorMessages = '';
	    $scope.errors = {};
	};

	$scope.initialize = function() {

		// clear errors fields
		$scope.successMessages = '';
		$scope.errorMessages = '';
		$scope.errors = {};

		if (location.pathname.search("j_security_check") != -1) {
			$timeout(function() {
				noty({
					layout : 'topRight',
					type : 'error',
					text : 'Login ou senha inválidos!'
				});
			}, 500);
		}
	};

	// close modal
	$scope.close = function(result) {
		$('#modalUsuario').modal('hide');
	};

	$scope.initialize();
	
	$scope.reset();

}