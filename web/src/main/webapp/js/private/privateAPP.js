var app = angular.module('transporte', ['ui.bootstrap', 'ui.utils', 'CEPService', 'UserService', 'PlanService', 'ClienteService', 'RoleService', 'InstituicaoService', 'EstudanteService', 'TransportadorService', 'ContratoService', 'PagamentoService', 'AssociacaoService', '$strap', 'ng', 'ui.select2']).config(
		[ '$routeProvider', '$dialogProvider', function($routeProvider, $dialogProvider) {

	// disable backdrop click for the dialog provider
	$dialogProvider.options({backdropClick: false});
			
	$routeProvider.when('/user', {
		templateUrl : 'administration/user/userList.xhtml',
		controller : UserCtrl
	}).when('/dashboard', {
		templateUrl : 'dashboard/dashboard.xhtml',
		controller : DashBoardCtrl
	}).when('/role', {
		templateUrl : 'administration/role/roleList.xhtml',
		controller : RoleCtrl
	}).when('/plan', {
		templateUrl : 'administration/plan/planList.xhtml',
		controller : PlanCtrl
	}).when('/cliente', {
		templateUrl : 'crud/cliente/clienteList.xhtml',
		controller : ClienteCtrl
	}).when('/instituicao', {
		templateUrl : 'crud/instituicao/instituicaoList.xhtml',
		controller : InstituicaoCtrl
	}).when('/estudante', {
		templateUrl : 'crud/estudante/estudanteList.xhtml',
		controller : EstudanteCtrl
	}).when('/contrato', {
		templateUrl : 'crud/contrato/contratoList.xhtml',
		controller : ContratoCtrl
	}).when('/pagamento', {
		templateUrl : 'action/pagamento/pagamentoList.xhtml',
		controller : PagamentoCtrl
	}).otherwise({
		redirectTo : '/dashboard'
	});
	
} ]);

app.filter('localtime', function() {
    return function(d) {
    	if(d) {
	    	var mDate = moment(d);
	    	return mDate.format('DD/MM/YYYY');
    	} else {
    		return null;
    	}
	    	
    }
});

app.filter('fone', function() {
    return function(str) {
    	if(str) {
	    	return "(" + str.substring(0,2) + ") " + str.substring(2, str.length-4) + "-" + str.substring(str.length-4, str.length);
    	} else {
    		return "";
    	}
	    	
    }
});

app.filter('cgc', function() {
    return function(str) {
    	if(str) {
    		if (str.length > 11)
    			return str.substring(0,2) + "." + str.substring(2,5) + "." + str.substring(5,8) + "/" + str.substring(8,12) + "-" + str.substring(12,14);
    		else{
    			return str.substring(0,3) + "." + str.substring(3,6) + "." + str.substring(6,9) + "-" + str.substring(9,11);
    		}
    	} else {
    		return "";
    	}
	    	
    }
});

app.filter('month', function() {
    return function(value) {
    	var months = new Array("", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
    	if (value != null)
    		return months[value];
    	return "";
    }
});

app.filter('currency', function() {
    return function(value) {
    	if(value) {
    		if (value.toString().indexOf(",") == -1)
    			value = "R$ " + value.toString() + ",00";
    		else {
    			value = "R$ " + value.toString();
    		}
    		
    		return value;
    	} else {
    		return "";
    	}
	    	
    }
});
