var app = angular.module('transporte');

app.directive('mksDatepicker', function() {
	return {
		restrict : 'C',
		scope: "true",
		link : function(scope, el, attr, ngModel) {
			scope.childCtrl = el.find('input').controller('ngModel');
			
			scope.$watch('childCtrl.$modelValue', function(){ 
				if (scope.childCtrl.$modelValue == '' && $(el).data('datetimepicker').getLocalDate() != undefined) {
					$(el).data('datetimepicker').setLocalDate(null);
				}
				 
				if(scope.childCtrl.$modelValue instanceof Date != true && scope.childCtrl.$modelValue != undefined) {
					if(scope.childCtrl.$modelValue.length > 9) { 
						var datawrapper = moment(scope.childCtrl.$modelValue, ['YYYY-MM-DD', 'DD/MM/YYYY']);
						datawrapper.local();
						
						$(el).data('datetimepicker').setLocalDate(datawrapper.toDate());
						scope.childCtrl.$setViewValue(datawrapper.toDate());
					}
				}
			});

			$(el).datetimepicker({
				language : 'pt-BR',
				format : 'dd/MM/yyyy',
				pickTime : false,
				maskInput : true
			});
			$(el).on('changeDate', function(e) {
				scope.childCtrl.$setViewValue(e.localDate);
				scope.$apply();
			});
			
			$(el).data('datetimepicker').setLocalDate(null);

		}
	};
});

app.directive('mksMap',['$timeout', '$compile', function(timer, compile) {
	return {
		restrict : 'E',
		replace : true,
//		template:function( elem,attrs ) {      
//	      return '<div id="divMap' + attrs.mapName + '" style="height: 300px; width: 550px"></div>';
//	    },
		scope: {
			endereco: '=',
			numero: '=',
			bairro: '=',
			cep: '=',
			estado: '=',
			cidade: '=',
			latitude: '=',
			longitude: '=',
			mapName: '@'
		},
		controller : function($scope, $element, $attrs,	$location) {
			
			$scope.$on('validateMap', function(event) {
				timer(function() {
					google.maps.event.trigger($scope.map, 'resize');
					$scope.searchAddress(); 
					
				},500);
			});
			
			$scope.searchAddress = function(){
				var searchValue = $scope.endereco + ", " + $scope.numero + " - " + $scope.bairro + ", " + $scope.cidade + " - " + $scope.estado + ", " + $scope.cep;
				 
				$scope.geocoder.geocode( 
					{'address': searchValue}, 
					function(results, status) {							
						if (status == google.maps.GeocoderStatus.OK) {
							$scope.map.setCenter(results[0].geometry.location);
							$scope.marker.setPosition(results[0].geometry.location);	        								
							
							$scope.latitude = results[0].geometry.location.lat();
							$scope.longitude = results[0].geometry.location.lng();
							$scope.$apply();
							$scope.$apply();
						} 
					}
				);
			};
			
			 
		},
		link: function($scope, element, attrs) {
			element.html('<div id="divMap' + attrs.mapName + '" style="height: 300px; width: 550px; display:block;"></div>').show();
			compile(element.contents())($scope);
			
			timer(function() {
				$scope.divIdName = "divMap" + attrs.mapName;
				$scope.geocoder = new google.maps.Geocoder();
				$scope.map;
				$scope.marker;
				$scope.latLng = new google.maps.LatLng(-13.97660241605429,-51.16132380456432);
				
				var zoomLevel = 15;

				google.maps.visualRefresh = true;

				$scope.map = new google.maps.Map(document.getElementById($scope.divIdName), {
	            	zoom: zoomLevel,
	            	center: $scope.latLng,
	            	mapTypeId: google.maps.MapTypeId.ROADMAP
	          	});
				
				
				$scope.marker = new google.maps.Marker({
	            	position: $scope.latLng,
	            	title: 'Residência',
	            	map: $scope.map,
	            	draggable: true
	          	});
				
				google.maps.event.addListener($scope.marker, "dragend", function () {
					$scope.latitude = $scope.marker.getPosition().lat();
					$scope.longitude = $scope.marker.getPosition().lng();
					$scope.mapValidation = false;
					$scope.$apply();
					$scope.$apply();
				});
				
			}, 0);
		}
	};
}]);

app.directive('ngFocus', ['$parse', function($parse) {
	return function(scope, element, attr) {
		var fn = $parse(attr['ngFocus']);
		element.bind('focus', function(event) {
			scope.$apply(function() {
				fn(scope, {$event:event});
			});
		});
	};
}]);
	 
app.directive('ngBlur', ['$parse', function($parse) {
	return function(scope, element, attr) {
		var fn = $parse(attr['ngBlur']);
		element.bind('blur', function(event) {
			scope.$apply(function() {
				fn(scope, {$event:event});
			});
		});
	};
}]);

app.directive('fileUpload', function () {
    return {
    	restrict: 'C',
        scope: true,        //create a new scope
        link: function (scope, el, attrs) {
            el.bind('change', function (event) {
                var files = event.target.files;
                //iterate files since 'multiple' may be specified on the element
                for (var i = 0;i<files.length;i++) {
                    //emit event upward
                    scope.$emit("fileSelected", { file: files[i] });
                }                                       
            });
        }
    };
});
