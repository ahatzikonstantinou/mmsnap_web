(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('IntentionsAndPlansDeleteController',IntentionsAndPlansDeleteController);

    IntentionsAndPlansDeleteController.$inject = ['$uibModalInstance', 'entity', 'IntentionsAndPlans'];

    function IntentionsAndPlansDeleteController($uibModalInstance, entity, IntentionsAndPlans) {
        var vm = this;

        vm.intentionsAndPlans = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            IntentionsAndPlans.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
