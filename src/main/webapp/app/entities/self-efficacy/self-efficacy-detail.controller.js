(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .controller('SelfEfficacyDetailController', SelfEfficacyDetailController);

    SelfEfficacyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SelfEfficacy', 'User'];

    function SelfEfficacyDetailController($scope, $rootScope, $stateParams, previousState, entity, SelfEfficacy, User) {
        var vm = this;

        vm.selfEfficacy = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mmsnapWebApp:selfEfficacyUpdate', function(event, result) {
            vm.selfEfficacy = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
