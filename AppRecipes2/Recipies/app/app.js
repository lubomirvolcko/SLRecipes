var myApp = angular.module('myApp',[]);


myApp.controller('PagesController', function($scope){


$scope.recipies =[
  {
    name: "spenat",
    stars: 4,
    color: "green"

},
{
  name: "brokolica",
  stars: 2,
  color: "black"

},

{
  name: "mrkva",
  stars: 4,
  color: "red"

}
];

});

var firstPage = angular.module('firstPage',[]);


firstPage.controller('registerLogin',function registerLogin($scope){

  $scope.register = 'register';
  $scope.login = 'login';

  var registerForm = function registerForm()
  {

    if (registerForm=true) {

    registerForm=show;

                            }

  }

});
