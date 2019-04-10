(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('WeeklyEvaluationDeleteController',WeeklyEvaluationDeleteController);

    WeeklyEvaluationDeleteController.$inject = ['$uibModalInstance', 'entity', 'WeeklyEvaluation'];

    function WeeklyEvaluationDeleteController($uibModalInstance, entity, WeeklyEvaluation) {
        var vm = this;

        vm.weeklyEvaluation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WeeklyEvaluation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
