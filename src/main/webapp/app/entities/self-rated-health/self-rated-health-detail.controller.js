(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('SelfRatedHealthDetailController', SelfRatedHealthDetailController);

    SelfRatedHealthDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SelfRatedHealth', 'User'];

    function SelfRatedHealthDetailController($scope, $rootScope, $stateParams, previousState, entity, SelfRatedHealth, User) {
        var vm = this;

        vm.selfRatedHealth = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mmsnapWebApp:selfRatedHealthUpdate', function(event, result) {
            vm.selfRatedHealth = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
