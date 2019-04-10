(function() {
    'use strict';
    angular
        .module('mmsnapWebApp')
        .factory('SelfEfficacy', SelfEfficacy);

    SelfEfficacy.$inject = ['$resource', 'DateUtils'];

    function SelfEfficacy ($resource, DateUtils) {
        var resourceUrl =  'api/self-efficacies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
