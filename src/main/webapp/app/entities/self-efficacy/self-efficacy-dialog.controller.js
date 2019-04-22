(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('SelfEfficacyDialogController', SelfEfficacyDialogController);

    SelfEfficacyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SelfEfficacy', 'User'];

    function SelfEfficacyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SelfEfficacy, User) {
        var vm = this;

        vm.selfEfficacy = entity;
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
//            console.log( vm.selfEfficacy );
            vm.selfEfficacy.healthierLifestyle = null != vm.selfEfficacy.healthierLifestyle ? vm.selfEfficacy.healthierLifestyle : false;
            vm.selfEfficacy.manageMultimorbidity = null != vm.selfEfficacy.manageMultimorbidity ? vm.selfEfficacy.manageMultimorbidity : false;
            vm.selfEfficacy.completeBehaviourGoals = null != vm.selfEfficacy.completeBehaviourGoals ? vm.selfEfficacy.completeBehaviourGoals : false;
            if (vm.selfEfficacy.id !== null) {
                SelfEfficacy.update(vm.selfEfficacy, onSaveSuccess, onSaveError);
            } else {
                SelfEfficacy.save(vm.selfEfficacy, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mmsnapWebApp:selfEfficacyUpdate', result);
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
