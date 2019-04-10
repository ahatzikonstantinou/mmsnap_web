(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('EQVasDeleteController',EQVasDeleteController);

    EQVasDeleteController.$inject = ['$uibModalInstance', 'entity', 'EQVas'];

    function EQVasDeleteController($uibModalInstance, entity, EQVas) {
        var vm = this;

        vm.eQVas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EQVas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
