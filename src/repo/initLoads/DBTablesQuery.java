package repo.initLoads;

public enum DBTablesQuery {

    CHECK_DB("SELECT 1 FROM pg_database WHERE datname = 'hms'"),
    CREATE_DB("CREATE DATABASE hms"),

    CHECK_TABLE("SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = '%s')"),

  
    CREATE_ROLES(
        """
        CREATE TABLE public.roles (
            id SERIAL ,
            rolename TEXT
        );

        ALTER TABLE ONLY public.roles
            ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
        """
    ),

 
    CREATE_USERS(
        """
        CREATE TABLE public.users (
            id SERIAL,
            username TEXT,
            email TEXT,
            password TEXT,
            role_id INTEGER,
            gender VARCHAR(10),
            phone TEXT,
            is_active BOOLEAN
        );

        ALTER TABLE ONLY public.users
            ADD CONSTRAINT users_pkey PRIMARY KEY (id);

        ALTER TABLE ONLY public.users
            ADD CONSTRAINT fk_roleid FOREIGN KEY (role_id) REFERENCES public.roles(id);
        """
    ),

   
    CREATE_DEPARTMENTS(
        """
        CREATE TABLE public.departments (
            id SERIAL,
            department_name TEXT
        );

        ALTER TABLE ONLY public.departments
            ADD CONSTRAINT departments_pkey PRIMARY KEY (id);
        """
    ),

  
    CREATE_DOCTOR_DETAILS(
        """
        CREATE TABLE public.doctor_details (
            id SERIAL,
            user_id INTEGER,
            department_id INTEGER,
            speciality TEXT
        );

        ALTER TABLE ONLY public.doctor_details
            ADD CONSTRAINT doctor_details_pkey PRIMARY KEY (id);
        """
    ),

   
    CREATE_PATIENT_DETAILS(
        """
        CREATE TABLE public.patient_details (
            id SERIAL,
            user_id INTEGER,
            blood_group VARCHAR(10),
            address TEXT
        );

        ALTER TABLE ONLY public.patient_details
            ADD CONSTRAINT patient_details_pkey PRIMARY KEY (id);

        ALTER TABLE ONLY public.patient_details
            ADD CONSTRAINT fk_userid FOREIGN KEY (user_id) REFERENCES public.users(id);
        """
    ),

   
    CREATE_LABTESTS(
        """
        CREATE TABLE public.labtests (
            id SERIAL,
            test_name TEXT,
            fees DOUBLE PRECISION
        );

        ALTER TABLE ONLY public.labtests
            ADD CONSTRAINT labtests_pkey PRIMARY KEY (id);
        """
    ),

   
    CREATE_SLOTS(
        """
        CREATE TABLE public.slots (
            id SERIAL,
            doctor_id INTEGER NOT NULL,
            slot_date DATE NOT NULL,
            start_time TIME WITHOUT TIME ZONE NOT NULL,
            end_time TIME WITHOUT TIME ZONE NOT NULL,
            status VARCHAR(20) DEFAULT 'FREE'
        );

        ALTER TABLE ONLY public.slots
            ADD CONSTRAINT slots_pkey PRIMARY KEY (id);
        """
    ),

  
    CREATE_APPOINTMENTS(
        """
        CREATE TABLE public.appointments (
            id SERIAL,
            doctor_id INTEGER NOT NULL,
            patient_id INTEGER NOT NULL,
            slot_id INTEGER NOT NULL,
            appointment_date DATE NOT NULL,
            appointment_time TIME WITHOUT TIME ZONE NOT NULL,
            status VARCHAR(20)
        );

        ALTER TABLE ONLY public.appointments
            ADD CONSTRAINT appointments_pkey PRIMARY KEY (id);

        ALTER TABLE ONLY public.appointments
            ADD CONSTRAINT appointments_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES public.users(id);

        ALTER TABLE ONLY public.appointments
            ADD CONSTRAINT appointments_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES public.users(id);

        ALTER TABLE ONLY public.appointments
            ADD CONSTRAINT appointments_slot_id_fkey FOREIGN KEY (slot_id) REFERENCES public.slots(id);
        """
    ),

    
    CREATE_PRESCRIPTIONS(
        """
        CREATE TABLE public.prescriptions (
            id SERIAL,
            appointment_id INTEGER NOT NULL,
            diagnosis TEXT NOT NULL,
            medications TEXT NOT NULL,
            instructions TEXT,
            prescribed_date DATE NOT NULL
        );

        ALTER TABLE ONLY public.prescriptions
            ADD CONSTRAINT prescriptions_pkey PRIMARY KEY (id);

        ALTER TABLE ONLY public.prescriptions
            ADD CONSTRAINT prescriptions_appointment_id_fkey FOREIGN KEY (appointment_id) REFERENCES public.appointments(id);
        """
    ),

   
    CREATE_LAB_REPORTS(
        """
        CREATE TABLE public.lab_reports (
            id SERIAL,
            lab_test_id INTEGER NOT NULL,
            appointment_id INTEGER NOT NULL,
            lab_technician_id INTEGER NOT NULL,
            test_result TEXT,
            created_at DATE NOT NULL,
            status VARCHAR(20)
        );

        ALTER TABLE ONLY public.lab_reports
            ADD CONSTRAINT lab_reports_pkey PRIMARY KEY (id);

        ALTER TABLE ONLY public.lab_reports
            ADD CONSTRAINT lab_reports_appointment_id_fkey FOREIGN KEY (appointment_id) REFERENCES public.appointments(id);

        ALTER TABLE ONLY public.lab_reports
            ADD CONSTRAINT lab_reports_lab_technician_id_fkey FOREIGN KEY (lab_technician_id) REFERENCES public.users(id);
        """
    ),

   INSERT_ROLES("INSERT INTO roles (rolename) VALUES ('admin')"),

    INSERT_USER("""
        INSERT INTO users (username, email, password, role_id, gender, phone, is_active)
        VALUES ('admin', 'admin@gmail.com', 'N*Q@Z)V<A*@&#@', 1, 'MALE', '8870865204', TRUE)
    """);

    private final String query;

    DBTablesQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }
}
