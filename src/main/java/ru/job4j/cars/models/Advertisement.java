package ru.job4j.cars.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Type(type = "text")
    @Column(nullable = false)
    private String description;

    @Column(name = "manufacture_year")
    private int year;

    private boolean status = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publication_date")
    private Date publicationDate ;

    @ManyToOne(optional = false)
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne(optional = false)
    @JoinColumn(name = "body_type_id")
    private BodyType bodyType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advertisement")
    Set<Photo> photos = new HashSet<>();

    public static Advertisement of(String description, int year, Model model, BodyType bodyType, User author) {
        Advertisement ad = new Advertisement();
        ad.setDescription(description);
        ad.setYear(year);
        ad.setModel(model);
        ad.setBodyType(bodyType);
        ad.setAuthor(author);
        ad.setPublicationDate(new Date());
        return ad;
    }

    public Advertisement() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", status=" + status +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
