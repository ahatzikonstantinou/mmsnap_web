(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('IntentionsAndPlansDetailController', IntentionsAndPlansDetailController);

    IntentionsAndPlansDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'IntentionsAndPlans', 'User'];

    function IntentionsAndPlansDetailController($scope, $rootScope, $stateParams, previousState, entity, IntentionsAndPlans, User) {
        var vm = this;

        vm.intentionsAndPlans = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mmsnapWebApp:intentionsAndPlansUpdate', function(event, result) {
            vm.intentionsAndPlans = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
