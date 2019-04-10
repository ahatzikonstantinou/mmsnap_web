(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('intentions-and-plans', {
            parent: 'entity',
            url: '/intentions-and-plans',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'IntentionsAndPlans'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/intentions-and-plans/intentions-and-plans.html',
                    controller: 'IntentionsAndPlansController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('intentions-and-plans-detail', {
            parent: 'intentions-and-plans',
            url: '/intentions-and-plans/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'IntentionsAndPlans'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/intentions-and-plans/intentions-and-plans-detail.html',
                    controller: 'IntentionsAndPlansDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'IntentionsAndPlans', function($stateParams, IntentionsAndPlans) {
                    return IntentionsAndPlans.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'intentions-and-plans',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('intentions-and-plans-detail.edit', {
            parent: 'intentions-and-plans-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/intentions-and-plans/intentions-and-plans-dialog.html',
                    controller: 'IntentionsAndPlansDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IntentionsAndPlans', function(IntentionsAndPlans) {
                            return IntentionsAndPlans.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('intentions-and-plans.new', {
            parent: 'intentions-and-plans',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/intentions-and-plans/intentions-and-plans-dialog.html',
                    controller: 'IntentionsAndPlansDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                whenToExercise: null,
                                pastWeekExercise: null,
                                exerciseWhere: null,
                                exerciseHow: null,
                                exerciseHowOfter: null,
                                exerciseWithWhom: null,
                                plansInterfere: null,
                                setbacksCope: null,
                                difficultSituations: null,
                                goodOpportunities: null,
                                preventLapses: null,
                                exerciseSeveralTimesPerWeek: null,
                                workUpSweat: null,
                                exerciseRegularly: null,
                                minimumPhysicalActivity: null,
                                leisureTimeActivity: null,
                                exerciseDuringRehabilitation: null,
                                phase: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('intentions-and-plans', null, { reload: 'intentions-and-plans' });
                }, function() {
                    $state.go('intentions-and-plans');
                });
            }]
        })
        .state('intentions-and-plans.edit', {
            parent: 'intentions-and-plans',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/intentions-and-plans/intentions-and-plans-dialog.html',
                    controller: 'IntentionsAndPlansDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IntentionsAndPlans', function(IntentionsAndPlans) {
                            return IntentionsAndPlans.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('intentions-and-plans', null, { reload: 'intentions-and-plans' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('intentions-and-plans.delete', {
            parent: 'intentions-and-plans',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/intentions-and-plans/intentions-and-plans-delete-dialog.html',
                    controller: 'IntentionsAndPlansDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IntentionsAndPlans', function(IntentionsAndPlans) {
                            return IntentionsAndPlans.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('intentions-and-plans', null, { reload: 'intentions-and-plans' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
