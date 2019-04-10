(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('health-risk', {
            parent: 'entity',
            url: '/health-risk',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HealthRisks'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/health-risk/health-risks.html',
                    controller: 'HealthRiskController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('health-risk-detail', {
            parent: 'health-risk',
            url: '/health-risk/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HealthRisk'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/health-risk/health-risk-detail.html',
                    controller: 'HealthRiskDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'HealthRisk', function($stateParams, HealthRisk) {
                    return HealthRisk.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'health-risk',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('health-risk-detail.edit', {
            parent: 'health-risk-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/health-risk/health-risk-dialog.html',
                    controller: 'HealthRiskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HealthRisk', function(HealthRisk) {
                            return HealthRisk.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('health-risk.new', {
            parent: 'health-risk',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/health-risk/health-risk-dialog.html',
                    controller: 'HealthRiskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                smoking: null,
                                physicalActivity: null,
                                diet: null,
                                alcohol: null,
                                phase: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('health-risk', null, { reload: 'health-risk' });
                }, function() {
                    $state.go('health-risk');
                });
            }]
        })
        .state('health-risk.edit', {
            parent: 'health-risk',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/health-risk/health-risk-dialog.html',
                    controller: 'HealthRiskDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HealthRisk', function(HealthRisk) {
                            return HealthRisk.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('health-risk', null, { reload: 'health-risk' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('health-risk.delete', {
            parent: 'health-risk',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/health-risk/health-risk-delete-dialog.html',
                    controller: 'HealthRiskDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HealthRisk', function(HealthRisk) {
                            return HealthRisk.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('health-risk', null, { reload: 'health-risk' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
