(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('DailyEvaluationDialogController', DailyEvaluationDialogController);

    DailyEvaluationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DailyEvaluation', 'User'];

    function DailyEvaluationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DailyEvaluation, User) {
        var vm = this;

        vm.dailyEvaluation = entity;
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
            if (vm.dailyEvaluation.id !== null) {
                DailyEvaluation.update(vm.dailyEvaluation, onSaveSuccess, onSaveError);
            } else {
                DailyEvaluation.save(vm.dailyEvaluation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mmsnapWebApp:dailyEvaluationUpdate', result);
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
