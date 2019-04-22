(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('IntentionsAndPlansDialogController', IntentionsAndPlansDialogController);

    IntentionsAndPlansDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'IntentionsAndPlans', 'User'];

    function IntentionsAndPlansDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, IntentionsAndPlans, User) {
        var vm = this;

        vm.intentionsAndPlans = entity;
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
            vm.intentionsAndPlans.difficultSituations = null != vm.intentionsAndPlans.difficultSituations ? vm.intentionsAndPlans.difficultSituations : false;
            vm.intentionsAndPlans.exerciseDuringRehabilitation = null != vm.intentionsAndPlans.exerciseDuringRehabilitation ? vm.intentionsAndPlans.exerciseDuringRehabilitation : false;
            vm.intentionsAndPlans.exerciseHow = null != vm.intentionsAndPlans.exerciseHow ? vm.intentionsAndPlans.exerciseHow : false;
            vm.intentionsAndPlans.exerciseHowOften = null != vm.intentionsAndPlans.exerciseHowOften ? vm.intentionsAndPlans.exerciseHowOften : false;
            vm.intentionsAndPlans.exerciseRegularly = null != vm.intentionsAndPlans.exerciseRegularly ? vm.intentionsAndPlans.exerciseRegularly : false;
            vm.intentionsAndPlans.exerciseSeveralTimesPerWeek = null != vm.intentionsAndPlans.exerciseSeveralTimesPerWeek ? vm.intentionsAndPlans.exerciseSeveralTimesPerWeek : false;
            vm.intentionsAndPlans.exerciseWhere = null != vm.intentionsAndPlans.exerciseWhere ? vm.intentionsAndPlans.exerciseWhere : false;
            vm.intentionsAndPlans.exerciseWithWhom = null != vm.intentionsAndPlans.exerciseWithWhom ? vm.intentionsAndPlans.exerciseWithWhom : false;
            vm.intentionsAndPlans.goodOpportunities = null != vm.intentionsAndPlans.goodOpportunities ? vm.intentionsAndPlans.goodOpportunities : false;
            vm.intentionsAndPlans.leisureTimeActivity = null != vm.intentionsAndPlans.leisureTimeActivity ? vm.intentionsAndPlans.leisureTimeActivity : false;
            vm.intentionsAndPlans.minimumPhysicalActivity = null != vm.intentionsAndPlans.minimumPhysicalActivity ? vm.intentionsAndPlans.minimumPhysicalActivity : false;
            vm.intentionsAndPlans.pastWeekExercise = null != vm.intentionsAndPlans.pastWeekExercise ? vm.intentionsAndPlans.pastWeekExercise : false;
            vm.intentionsAndPlans.plansInterfere = null != vm.intentionsAndPlans.plansInterfere ? vm.intentionsAndPlans.plansInterfere : false;
            vm.intentionsAndPlans.preventLapses = null != vm.intentionsAndPlans.preventLapses ? vm.intentionsAndPlans.preventLapses : false;
            vm.intentionsAndPlans.setbacksCope = null != vm.intentionsAndPlans.setbacksCope ? vm.intentionsAndPlans.setbacksCope : false;
            vm.intentionsAndPlans.whenToExercise = null != vm.intentionsAndPlans.whenToExercise ? vm.intentionsAndPlans.whenToExercise : false;
            vm.intentionsAndPlans.workUpSweat = null != vm.intentionsAndPlans.workUpSweat ? vm.intentionsAndPlans.workUpSweat : false;
//            console.log( vm.intentionsAndPlans );
            if (vm.intentionsAndPlans.id !== null) {
                IntentionsAndPlans.update(vm.intentionsAndPlans, onSaveSuccess, onSaveError);
            } else {
                IntentionsAndPlans.save(vm.intentionsAndPlans, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mmsnapWebApp:intentionsAndPlansUpdate', result);
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
