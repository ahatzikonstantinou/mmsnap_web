(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('WeeklyEvaluationDialogController', WeeklyEvaluationDialogController);

    WeeklyEvaluationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WeeklyEvaluation', 'User'];

    function WeeklyEvaluationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WeeklyEvaluation, User) {
        var vm = this;

        vm.weeklyEvaluation = entity;
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
            if (vm.weeklyEvaluation.id !== null) {
                WeeklyEvaluation.update(vm.weeklyEvaluation, onSaveSuccess, onSaveError);
            } else {
                WeeklyEvaluation.save(vm.weeklyEvaluation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mmsnapWebApp:weeklyEvaluationUpdate', result);
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
