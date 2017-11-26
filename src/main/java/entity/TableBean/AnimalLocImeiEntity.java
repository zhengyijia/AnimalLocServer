package entity.TableBean;

public class AnimalLocImeiEntity {
    private String petId;
    private String imei;

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimalLocImeiEntity that = (AnimalLocImeiEntity) o;

        if (petId != null ? !petId.equals(that.petId) : that.petId != null) return false;
        if (imei != null ? !imei.equals(that.imei) : that.imei != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = petId != null ? petId.hashCode() : 0;
        result = 31 * result + (imei != null ? imei.hashCode() : 0);
        return result;
    }
}
