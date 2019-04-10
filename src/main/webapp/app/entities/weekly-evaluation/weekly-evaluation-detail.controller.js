(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('WeeklyEvaluationDetailController', WeeklyEvaluationDetailController);

    WeeklyEvaluationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WeeklyEvaluation', 'User'];

    function WeeklyEvaluationDetailController($scope, $rootScope, $stateParams, previousState, entity, WeeklyEvaluation, User) {
        var vm = this;

        vm.weeklyEvaluation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mmsnapWebApp:weeklyEvaluationUpdate', function(event, result) {
            vm.weeklyEvaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
