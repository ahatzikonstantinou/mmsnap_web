(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', '$http','AuthServerProvider'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService, $http, AuthServerProvider) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.exportToExcel = exportToExcel;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        function exportToExcel( $event )
        {
//            console.log( 'AuthServerProvider.getToken ', AuthServerProvider.getToken() );

            var req = {
             method: 'GET',
             url: 'api/_export',
             headers: {
               'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
               'Authorization': 'Bearer ' + AuthServerProvider.getToken()
             },
             responseType: 'arraybuffer'
            }

            $http(req).then(
                function( response )
                {
//                    console.log( 'success, response: ', response );
                    var filename = response.headers('Content-Disposition').split(";")[1].split("=")[1];
                    console.log( 'filename: ', filename );
                    var file = new File( [ response.data ], filename, { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' } );
                    var fileURL = URL.createObjectURL( file );
                    window.open( fileURL );
                },
                function()
                {
                    console.log( 'failure' );
                }
            );
        }
    }
})();
