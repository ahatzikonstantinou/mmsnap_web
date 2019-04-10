(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('eq-vas', {
            parent: 'entity',
            url: '/eq-vas',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EQVas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/e-q-vas/e-q-vas.html',
                    controller: 'EQVasController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('eq-vas-detail', {
            parent: 'eq-vas',
            url: '/eq-vas/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EQVas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/e-q-vas/eq-vas-detail.html',
                    controller: 'EQVasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EQVas', function($stateParams, EQVas) {
                    return EQVas.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'eq-vas',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('eq-vas-detail.edit', {
            parent: 'eq-vas-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-q-vas/eq-vas-dialog.html',
                    controller: 'EQVasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EQVas', function(EQVas) {
                            return EQVas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eq-vas.new', {
            parent: 'eq-vas',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-q-vas/eq-vas-dialog.html',
                    controller: 'EQVasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                score: null,
                                phase: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('eq-vas', null, { reload: 'eq-vas' });
                }, function() {
                    $state.go('eq-vas');
                });
            }]
        })
        .state('eq-vas.edit', {
            parent: 'eq-vas',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-q-vas/eq-vas-dialog.html',
                    controller: 'EQVasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EQVas', function(EQVas) {
                            return EQVas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eq-vas', null, { reload: 'eq-vas' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eq-vas.delete', {
            parent: 'eq-vas',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-q-vas/eq-vas-delete-dialog.html',
                    controller: 'EQVasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EQVas', function(EQVas) {
                            return EQVas.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eq-vas', null, { reload: 'eq-vas' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
