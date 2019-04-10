(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('self-rated-health', {
            parent: 'entity',
            url: '/self-rated-health',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SelfRatedHealths'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/self-rated-health/self-rated-healths.html',
                    controller: 'SelfRatedHealthController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('self-rated-health-detail', {
            parent: 'self-rated-health',
            url: '/self-rated-health/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SelfRatedHealth'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/self-rated-health/self-rated-health-detail.html',
                    controller: 'SelfRatedHealthDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SelfRatedHealth', function($stateParams, SelfRatedHealth) {
                    return SelfRatedHealth.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'self-rated-health',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('self-rated-health-detail.edit', {
            parent: 'self-rated-health-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/self-rated-health/self-rated-health-dialog.html',
                    controller: 'SelfRatedHealthDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SelfRatedHealth', function(SelfRatedHealth) {
                            return SelfRatedHealth.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('self-rated-health.new', {
            parent: 'self-rated-health',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/self-rated-health/self-rated-health-dialog.html',
                    controller: 'SelfRatedHealthDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                oneConditionMoreSerious: null,
                                timeSpentManaging: null,
                                feelOverwhelmed: null,
                                causesAreLinked: null,
                                difficultAllMedications: null,
                                limitedActivities: null,
                                differentMedicationsProblems: null,
                                mixingMedications: null,
                                lessEffectiveTreatments: null,
                                oneCauseAnother: null,
                                oneDominates: null,
                                conditionsInteract: null,
                                difficultBestTreatment: null,
                                reducedSocialLife: null,
                                unhappy: null,
                                anxious: null,
                                angry: null,
                                sad: null,
                                irritable: null,
                                sadStruggle: null,
                                phase: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('self-rated-health', null, { reload: 'self-rated-health' });
                }, function() {
                    $state.go('self-rated-health');
                });
            }]
        })
        .state('self-rated-health.edit', {
            parent: 'self-rated-health',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/self-rated-health/self-rated-health-dialog.html',
                    controller: 'SelfRatedHealthDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SelfRatedHealth', function(SelfRatedHealth) {
                            return SelfRatedHealth.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('self-rated-health', null, { reload: 'self-rated-health' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('self-rated-health.delete', {
            parent: 'self-rated-health',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/self-rated-health/self-rated-health-delete-dialog.html',
                    controller: 'SelfRatedHealthDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SelfRatedHealth', function(SelfRatedHealth) {
                            return SelfRatedHealth.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('self-rated-health', null, { reload: 'self-rated-health' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
