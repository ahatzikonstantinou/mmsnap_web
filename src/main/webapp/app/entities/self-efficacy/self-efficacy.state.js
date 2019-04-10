(function() {
    'use strict';

    angular
        .module('mmsnapWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('self-efficacy', {
            parent: 'entity',
            url: '/self-efficacy',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SelfEfficacies'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/self-efficacy/self-efficacies.html',
                    controller: 'SelfEfficacyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('self-efficacy-detail', {
            parent: 'self-efficacy',
            url: '/self-efficacy/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SelfEfficacy'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/self-efficacy/self-efficacy-detail.html',
                    controller: 'SelfEfficacyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SelfEfficacy', function($stateParams, SelfEfficacy) {
                    return SelfEfficacy.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'self-efficacy',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('self-efficacy-detail.edit', {
            parent: 'self-efficacy-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/self-efficacy/self-efficacy-dialog.html',
                    controller: 'SelfEfficacyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SelfEfficacy', function(SelfEfficacy) {
                            return SelfEfficacy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('self-efficacy.new', {
            parent: 'self-efficacy',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/self-efficacy/self-efficacy-dialog.html',
                    controller: 'SelfEfficacyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                healthierLifestyle: null,
                                completeBehaviourGoals: null,
                                manageMultimorbidity: null,
                                phase: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('self-efficacy', null, { reload: 'self-efficacy' });
                }, function() {
                    $state.go('self-efficacy');
                });
            }]
        })
        .state('self-efficacy.edit', {
            parent: 'self-efficacy',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/self-efficacy/self-efficacy-dialog.html',
                    controller: 'SelfEfficacyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SelfEfficacy', function(SelfEfficacy) {
                            return SelfEfficacy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('self-efficacy', null, { reload: 'self-efficacy' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('self-efficacy.delete', {
            parent: 'self-efficacy',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/self-efficacy/self-efficacy-delete-dialog.html',
                    controller: 'SelfEfficacyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SelfEfficacy', function(SelfEfficacy) {
                            return SelfEfficacy.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('self-efficacy', null, { reload: 'self-efficacy' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
