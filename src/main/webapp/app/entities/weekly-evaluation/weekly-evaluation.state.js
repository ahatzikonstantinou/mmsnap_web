(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('weekly-evaluation', {
            parent: 'entity',
            url: '/weekly-evaluation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WeeklyEvaluations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weekly-evaluation/weekly-evaluations.html',
                    controller: 'WeeklyEvaluationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('weekly-evaluation-detail', {
            parent: 'weekly-evaluation',
            url: '/weekly-evaluation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WeeklyEvaluation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weekly-evaluation/weekly-evaluation-detail.html',
                    controller: 'WeeklyEvaluationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WeeklyEvaluation', function($stateParams, WeeklyEvaluation) {
                    return WeeklyEvaluation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'weekly-evaluation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('weekly-evaluation-detail.edit', {
            parent: 'weekly-evaluation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weekly-evaluation/weekly-evaluation-dialog.html',
                    controller: 'WeeklyEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WeeklyEvaluation', function(WeeklyEvaluation) {
                            return WeeklyEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('weekly-evaluation.new', {
            parent: 'weekly-evaluation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weekly-evaluation/weekly-evaluation-dialog.html',
                    controller: 'WeeklyEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                diet: null,
                                smoking: null,
                                physicalActivity: null,
                                alcohol: null,
                                year: null,
                                weekOfYear: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('weekly-evaluation', null, { reload: 'weekly-evaluation' });
                }, function() {
                    $state.go('weekly-evaluation');
                });
            }]
        })
        .state('weekly-evaluation.edit', {
            parent: 'weekly-evaluation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weekly-evaluation/weekly-evaluation-dialog.html',
                    controller: 'WeeklyEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WeeklyEvaluation', function(WeeklyEvaluation) {
                            return WeeklyEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weekly-evaluation', null, { reload: 'weekly-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('weekly-evaluation.delete', {
            parent: 'weekly-evaluation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weekly-evaluation/weekly-evaluation-delete-dialog.html',
                    controller: 'WeeklyEvaluationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WeeklyEvaluation', function(WeeklyEvaluation) {
                            return WeeklyEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weekly-evaluation', null, { reload: 'weekly-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
