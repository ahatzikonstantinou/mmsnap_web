(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('SelfEfficacyDeleteController',SelfEfficacyDeleteController);

    SelfEfficacyDeleteController.$inject = ['$uibModalInstance', 'entity', 'SelfEfficacy'];

    function SelfEfficacyDeleteController($uibModalInstance, entity, SelfEfficacy) {
        var vm = this;

        vm.selfEfficacy = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SelfEfficacy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
