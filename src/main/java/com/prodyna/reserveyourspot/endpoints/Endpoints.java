package com.prodyna.reserveyourspot.endpoints;

public interface Endpoints {
    interface User {
        String USER_ROOT = "/api/users";
        String GET_ALL = "/";
        String GET_BY_ID = "/{userId}";
        String CREATE = "/";
        String UPDATE = "/";
        String UPDATE_ROLE = "/{userId}/role/{role}";
        String DELETE = "/{userId}";
    }

    interface OfficeSpace {
        String OFFICE_SPACE_ROOT = "/api/office-spaces";
        String GET_ALL = "/";
        String GET_BY_OFFICE_SPACE_ID = "/{officeSpaceId}";
        String CREATE = "/";
        String UPDATE = "/";
        String DELETE = "/{officeSpaceId}";
    }

    interface OfficeRoom {
        String OFFICE_ROOM_ROOT = "/api/office-rooms";
        String GET_ALL = "/";
        String GET_BY_OFFICE_ROOM_ID = "/{officeRoomId}";
        String GET_BY_OFFICE_SPACE_ID = "/{officeSpaceId}/office-rooms";
        String CREATE = "/{officeSpaceId}";
        String UPDATE = "/";
        String DELETE = "/{officeRoomId}";
    }

    interface WorkStation {
        String OFFICE_ROOM_ROOT = "/api/work-stations/";
        String GET_ALL = "/";
        String GET_BY_WORK_STATION_ID = "/{workStationId}";
        String GET_BY_OFFICE_ROOM_ID = "/{officeRoomId}/work-stations";
        String CREATE = "/{officeRoomId}";
        String UPDATE = "/";
        String DELETE = "/{workStationId}";
    }

    interface Reservation {
        String RESERVATION_ROOT = "/api/reservations";
        String GET_ALL = "/";
        String CREATE = "/";
        String CREATE_IN_RANGE = "/in-range/dateFrom/{dateFrom}/dateTo/{dateTo}";
        String DELETE = "/{reservationId}";
    }
}
