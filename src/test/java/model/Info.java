package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Info {
    private String name;
    private String owner;
    private int age;
    private Passport passport;
    private List<String> details;

    public static class Passport {
        private String id;

        @JsonProperty("isValid")
        private boolean valid;

        public String getId() {
            return id;
        }

        public boolean isValid() {
            return valid;
        }
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public int getAge() {
        return age;
    }

    public Passport getPassport() {
        return passport;
    }

    public String getPassportId() {
        return passport != null ? passport.getId() : null;
    }

    public boolean isPassportValid() {
        return passport != null && passport.isValid();
    }

    public List<String> getDetails() {
        return details;
    }
}
