(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('SelfRatedHealthDialogController', SelfRatedHealthDialogController);

    SelfRatedHealthDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SelfRatedHealth', 'User'];

    function SelfRatedHealthDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SelfRatedHealth, User) {
        var vm = this;

        vm.selfRatedHealth = entity;
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
            if (vm.selfRatedHealth.id !== null) {
                SelfRatedHealth.update(vm.selfRatedHealth, onSaveSuccess, onSaveError);
            } else {
                SelfRatedHealth.save(vm.selfRatedHealth, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mmsnapWebApp:selfRatedHealthUpdate', result);
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
