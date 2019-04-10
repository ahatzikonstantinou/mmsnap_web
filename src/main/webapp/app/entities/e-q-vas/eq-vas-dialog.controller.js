(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('EQVasDialogController', EQVasDialogController);

    EQVasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EQVas', 'User'];

    function EQVasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EQVas, User) {
        var vm = this;

        vm.eQVas = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.eQVas.id !== null) {
                EQVas.update(vm.eQVas, onSaveSuccess, onSaveError);
            } else {
                EQVas.save(vm.eQVas, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mmsnapWebApp:eQVasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
