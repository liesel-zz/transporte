var app = angular.module('transporte', ['ui.bootstrap', 'ui.utils', 'UserService', '$strap', 'ng'])
				 .config(['$routeProvider', '$dialogProvider', 
				          function($routeProvider, $dialogProvider){
		
					 		  $dialogProvider.options({backdropClick: false});
				 		  }
]);