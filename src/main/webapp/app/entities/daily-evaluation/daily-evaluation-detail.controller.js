(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('DailyEvaluationDetailController', DailyEvaluationDetailController);

    DailyEvaluationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DailyEvaluation', 'User'];

    function DailyEvaluationDetailController($scope, $rootScope, $stateParams, previousState, entity, DailyEvaluation, User) {
        var vm = this;

        vm.dailyEvaluation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mmsnapWebApp:dailyEvaluationUpdate', function(event, result) {
            vm.dailyEvaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
