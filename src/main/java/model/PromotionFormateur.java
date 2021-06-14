package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "promotion_formateur", catalog = "evaly")
//@Table(name = "promotion_formateur", catalog = "u575564338_evaly")
public class PromotionFormateur implements java.io.Serializable {

	private Integer idPromotionFormateur;
	private Formateur formateur;
	private Promotion promotion;

	public PromotionFormateur() {
	}

	public PromotionFormateur(Formateur formateur, Promotion promotion) {
		this.formateur = formateur;
		this.promotion = promotion;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_promotion_formateur", unique = true, nullable = false)
	public Integer getIdPromotionFormateur() {
		return this.idPromotionFormateur;
	}

	public void setIdPromotionFormateur(Integer idPromotionFormateur) {
		this.idPromotionFormateur = idPromotionFormateur;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_formateur")
	public Formateur getFormateur() {
		return this.formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_promotion")
	public Promotion getPromotion() {
		return this.promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

}