package co.myappvalid;

public class Ubicacion {
    private String country;
    private int page;
    private Double perPage;
    private int totalPages;
    private Double totalReg;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Double getPerPage() {
        return perPage;
    }

    public void setPerPage(Double perPage) {
        this.perPage = perPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Double getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(Double totalReg) {
        this.totalReg = totalReg;
    }
}
