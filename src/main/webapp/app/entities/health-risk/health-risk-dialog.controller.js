(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('HealthRiskDialogController', HealthRiskDialogController);

    HealthRiskDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'HealthRisk', 'User'];

    function HealthRiskDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, HealthRisk, User) {
        var vm = this;

        vm.healthRisk = entity;
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
            if (vm.healthRisk.id !== null) {
                HealthRisk.update(vm.healthRisk, onSaveSuccess, onSaveError);
            } else {
                HealthRisk.save(vm.healthRisk, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mmsnapWebApp:healthRiskUpdate', result);
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
