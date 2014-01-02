'use strict';

// SECURITY
angular.module('UserService', ['ngResource'])
.factory('UserService', ['$resource', function($resource) {
  return $resource('/Transporte/webservices/rest/user/:action/:id', {
    id : '@id'
  }, {
	register : {
			 method: 'POST',
		     params: {action: 'register'}
	},
	save : {
		 method: 'POST',
	     params: {action: 'save'}
	},
	lostPassword : {
		 method: 'POST',
	     params: {action: 'lostPassword'}
	},
	resetPassword : {
		 method: 'POST',
	     params: {action: 'resetPassword'}
	},
	update: {
    	method: 'PUT',
    	params: {action: 'update'}
    },
	remove : {
		 method: 'DELETE',
	     params: {action: 'remove'}
	},
	findAll : {
	      method: 'GET',
	      isArray: true,
	      params: {action: 'findAll'}
	},
	findLoggedUser: {
		method: 'GET',
		isArray: false,
		params: {action: 'findLoggedUser'}
	},
	findById : {
	      method: 'GET',
	      isArray: false,
	      params: {action: 'findById'}
	},
    findByFilter : {
            method: 'GET',
            isArray: false,
            params : {action: 'findByFilter'}
    }
  });
}]);

angular.module('PlanService', ['ngResource'])
.factory('PlanService', ['$resource', function($resource) {
  return $resource('/Transporte/webservices/rest/plan/:action/:id', {
    id : '@id'
  }, {
	save : {
		 method: 'POST',
	     params: {action: 'save'}
	},
	update: {
    	method: 'PUT',
    	params: {action: 'update'}
    },
	remove : {
		 method: 'DELETE',
	     params: {action: 'remove'}
	},
	findAll : {
	      method: 'GET',
	      isArray: true,
	      params: {action: 'findAll'}
	},
	findById : {
	      method: 'GET',
	      isArray: false,
	      params: {action: 'findById'}
	},
    findByFilter : {
            method: 'GET',
            isArray: false,
            params : {action: 'findByFilter'}
    }
  });
}]);

angular.module('RoleService', ['ngResource'])
.factory('RoleService', ['$resource', function($resource) {
  return $resource('/Transporte/webservices/rest/role/:action/:id', {
    id : '@id'
  }, {
	save : {
		 method: 'POST',
	     params: {action: 'save'}
	},
	update: {
    	method: 'PUT',
    	params: {action: 'update'}
    },
	remove : {
		 method: 'DELETE',
	     params: {action: 'remove'}
	},
	findAll : {
	      method: 'GET',
	      isArray: true,
	      params: {action: 'findAll'}
	},
	findById : {
	      method: 'GET',
	      isArray: false,
	      params: {action: 'findById'}
	},
    findByFilter : {
            method: 'GET',
            isArray: false,
            params : {action: 'findByFilter'}
    }
  });
}]);

angular.module('ClienteService', ['ngResource'])
.factory('ClienteService', ['$resource', function($resource) {
  return $resource('/Transporte/webservices/rest/cliente/:action/:id', {
    id : '@id'
  }, {
	save : {
		 method: 'POST',
	     params: {action: 'save'}
	},
	update: {
    	method: 'PUT',
    	params: {action: 'update'}
    },
	remove : {
		 method: 'DELETE',
	     params: {action: 'remove'}
	},
	findAll : {
	      method: 'GET',
	      isArray: true,
	      params: {action: 'findAll'}
	},
	findById : {
	      method: 'GET',
	      isArray: false,
	      params: {action: 'findById'}
	},
    findByFilter : {
            method: 'GET',
            isArray: false,
            params : {action: 'findByFilter'}
    }
  });
}]);

angular.module('InstituicaoService', ['ngResource'])
.factory('InstituicaoService', ['$resource', function($resource) {
  return $resource('/Transporte/webservices/rest/instituicao/:action/:id', {
    id : '@id'
  }, {
	save : {
		 method: 'POST',
	     params: {action: 'save'}
	},
	update: {
    	method: 'PUT',
    	params: {action: 'update'}
    },
	remove : {
		 method: 'DELETE',
	     params: {action: 'remove'}
	},
	findAll : {
	      method: 'GET',
	      isArray: true,
	      params: {action: 'findAll'}
	},
	findById : {
	      method: 'GET',
	      isArray: false,
	      params: {action: 'findById'}
	},
    findByFilter : {
            method: 'GET',
            isArray: false,
            params : {action: 'findByFilter'}
    }
  });
}]);

angular.module('EstudanteService', ['ngResource'])
.factory('EstudanteService', ['$resource', function($resource) {
  return $resource('/Transporte/webservices/rest/estudante/:action/:id', {
    id : '@id'
  }, {
	save : {
		 method: 'POST',
	     params: {action: 'save'}
	},
	update: {
    	method: 'PUT',
    	params: {action: 'update'}
    },
	remove : {
		 method: 'DELETE',
	     params: {action: 'remove'}
	},
	findAll : {
	      method: 'GET',
	      isArray: true,
	      params: {action: 'findAll'}
	},
	findById : {
	      method: 'GET',
	      isArray: false,
	      params: {action: 'findById'}
	},
    findByFilter : {
            method: 'GET',
            isArray: false,
            params : {action: 'findByFilter'}
    }
  });
}]);

angular.module('TransportadorService', ['ngResource'])
.factory('TransportadorService', ['$resource', function($resource) {
	return $resource('/Transporte/webservices/rest/transportador/:action/:id', {
		id : '@id'
	}, {
		save : {
			method: 'POST',
			params: {action: 'save'}
		},
		update: {
			method: 'PUT',
			params: {action: 'update'}
		},
		remove : {
			method: 'DELETE',
			params: {action: 'remove'}
		},
		findByLoggedUser: {
			method: 'GET',
			isArray: false,
			params: {action: 'findByLoggedUser'}
		}
	});
}]);

angular.module('ContratoService', ['ngResource'])
.factory('ContratoService', ['$resource', function($resource) {
  return $resource('/Transporte/webservices/rest/contrato/:action/:id', {
    id : '@id'
  }, {
	register : {
			 method: 'POST',
		     params: {action: 'register'}
	},
	save : {
		 method: 'POST',
	     params: {action: 'save'}
	},
	update: {
    	method: 'PUT',
    	params: {action: 'update'}
    },
	remove : {
		 method: 'DELETE',
	     params: {action: 'remove'}
	},
	findAll : {
	      method: 'GET',
	      isArray: true,
	      params: {action: 'findAll'}
	},
	findById : {
	      method: 'GET',
	      isArray: false,
	      params: {action: 'findById'}
	},
	findByFilter : {
        method: 'GET',
        isArray: false,
        params : {action: 'findByFilter'}
	}
  });
}]);

angular.module('PagamentoService', ['ngResource'])
.factory('PagamentoService', ['$resource', function($resource) {
  return $resource('/Transporte/webservices/rest/pagamento/:action/:id', {
    id : '@id'
  }, {
	createByContrato : {
			 method: 'POST',
		     params: {action: 'createByContrato'}
	},
	findByContrato : {
	      method: 'GET',
	      isArray: false,
	      params: {action: 'findByContrato'}
	},
	doPayment: {
    	method: 'PUT',
    	params: {action: 'doPayment'}
    },
	dismissPayment: {
    	method: 'PUT',
    	params: {action: 'dismissPayment'}
    },
	removeByContrato : {
   		 method: 'DELETE',
   	     params: {action: 'removeByContrato'}
   	}
  });
}]);

angular.module('AssociacaoService', ['ngResource'])
.factory('AssociacaoService', ['$resource', function($resource) {
  return $resource('/Transporte/webservices/rest/contrato/:action/:id', {
    id : '@id'
  }, {
	findByFilter : {
        method: 'GET',
        isArray: false,
        params : {action: 'findByFilter'}
	}
  });
}]);

angular.module('CEPService', ['ngResource'])
.factory('CEPService', ['$resource', function($resource) {
  return $resource('http://cep.paicon.com.br/jsonp/:cep',
		  		  {cep : '@cep'},
				   {
				     getAddressByCEP: {
				       method: 'JSONP',
				       params: {callback: 'JSON_CALLBACK'}
				     }
				   });
}]);
