package angolodelleidee.catalogovirtuale;

/**
 * Created by diego on 26/07/2017.
 */

public class ProductItem {
    private Prodotto product;

    public ProductItem(Prodotto inviteWrapper) {
        this.product = inviteWrapper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductItem that = (ProductItem) o;

        return product.equals(that.product);

    }

    public void setProduct(Prodotto product) {
        this.product = product;
    }

    public Prodotto getProduct() {
        return product;
    }

    @Override
    public int hashCode() {
        return this.product.hashCode();
    }
}
