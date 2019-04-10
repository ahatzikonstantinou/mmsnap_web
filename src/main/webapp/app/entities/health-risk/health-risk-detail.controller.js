(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('HealthRiskDetailController', HealthRiskDetailController);

    HealthRiskDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HealthRisk', 'User'];

    function HealthRiskDetailController($scope, $rootScope, $stateParams, previousState, entity, HealthRisk, User) {
        var vm = this;

        vm.healthRisk = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mmsnapWebApp:healthRiskUpdate', function(event, result) {
            vm.healthRisk = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
