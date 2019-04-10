(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('DailyEvaluationDeleteController',DailyEvaluationDeleteController);

    DailyEvaluationDeleteController.$inject = ['$uibModalInstance', 'entity', 'DailyEvaluation'];

    function DailyEvaluationDeleteController($uibModalInstance, entity, DailyEvaluation) {
        var vm = this;

        vm.dailyEvaluation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DailyEvaluation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
