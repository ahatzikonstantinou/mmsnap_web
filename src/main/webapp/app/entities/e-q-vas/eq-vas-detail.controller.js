(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('EQVasDetailController', EQVasDetailController);

    EQVasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EQVas', 'User'];

    function EQVasDetailController($scope, $rootScope, $stateParams, previousState, entity, EQVas, User) {
        var vm = this;

        vm.eQVas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mmsnapWebApp:eQVasUpdate', function(event, result) {
            vm.eQVas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
