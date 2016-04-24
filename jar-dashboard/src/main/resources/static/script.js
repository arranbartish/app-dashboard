angular.module("DashApp", ["ngAnimate","ui.bootstrap"])
    .config(["$httpProvider", function($httpProvider){

    }])
    .service("PurchaseService", function(){

            this.populatePurchases= function(scope, http) {

                //scope.purchases = [{ name : "mock1" }, { name : "mock1" }];
                http.get('/purchases')
                  //  http.get('/status')
                    .success(function(response){
                        scope.purchases = response;
                    });
            };
    })
    .controller("AppPurchases", function($scope, $http, $timeout, $log, PurchaseService){
        PurchaseService.populatePurchases($scope, $http);

        var poll = function () {
            $timeout(function(){
                PurchaseService.populatePurchases($scope, $http);
                poll();
            }, 5000);
            //}, 12000000);
        };
        poll();
    })
    .directive('purchases', function() {
        return {
            restrict: 'E',
            replace: 'false',
            scope: {
                purchase: '='
            },
            templateUrl: './templates/purchase.html'
        }
    });

