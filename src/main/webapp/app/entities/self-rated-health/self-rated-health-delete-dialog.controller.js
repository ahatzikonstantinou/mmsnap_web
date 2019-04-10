(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('SelfRatedHealthDeleteController',SelfRatedHealthDeleteController);

    SelfRatedHealthDeleteController.$inject = ['$uibModalInstance', 'entity', 'SelfRatedHealth'];

    function SelfRatedHealthDeleteController($uibModalInstance, entity, SelfRatedHealth) {
        var vm = this;

        vm.selfRatedHealth = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SelfRatedHealth.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
