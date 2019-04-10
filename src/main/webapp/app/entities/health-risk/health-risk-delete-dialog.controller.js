(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('HealthRiskDeleteController',HealthRiskDeleteController);

    HealthRiskDeleteController.$inject = ['$uibModalInstance', 'entity', 'HealthRisk'];

    function HealthRiskDeleteController($uibModalInstance, entity, HealthRisk) {
        var vm = this;

        vm.healthRisk = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            HealthRisk.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
