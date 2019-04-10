(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('daily-evaluation', {
            parent: 'entity',
            url: '/daily-evaluation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DailyEvaluations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/daily-evaluation/daily-evaluations.html',
                    controller: 'DailyEvaluationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('daily-evaluation-detail', {
            parent: 'daily-evaluation',
            url: '/daily-evaluation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DailyEvaluation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/daily-evaluation/daily-evaluation-detail.html',
                    controller: 'DailyEvaluationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DailyEvaluation', function($stateParams, DailyEvaluation) {
                    return DailyEvaluation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'daily-evaluation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('daily-evaluation-detail.edit', {
            parent: 'daily-evaluation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/daily-evaluation/daily-evaluation-dialog.html',
                    controller: 'DailyEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DailyEvaluation', function(DailyEvaluation) {
                            return DailyEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('daily-evaluation.new', {
            parent: 'daily-evaluation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/daily-evaluation/daily-evaluation-dialog.html',
                    controller: 'DailyEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                diet: null,
                                smoking: null,
                                physicalActivity: null,
                                alcohol: null,
                                ifStatement: null,
                                thenStatement: null,
                                copingIfStatement: null,
                                copingThenStatement: null,
                                success: false,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('daily-evaluation', null, { reload: 'daily-evaluation' });
                }, function() {
                    $state.go('daily-evaluation');
                });
            }]
        })
        .state('daily-evaluation.edit', {
            parent: 'daily-evaluation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/daily-evaluation/daily-evaluation-dialog.html',
                    controller: 'DailyEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DailyEvaluation', function(DailyEvaluation) {
                            return DailyEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('daily-evaluation', null, { reload: 'daily-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('daily-evaluation.delete', {
            parent: 'daily-evaluation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/daily-evaluation/daily-evaluation-delete-dialog.html',
                    controller: 'DailyEvaluationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DailyEvaluation', function(DailyEvaluation) {
                            return DailyEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('daily-evaluation', null, { reload: 'daily-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
