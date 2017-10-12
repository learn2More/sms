package com.ppdai.ac.sms.consumer.core.model.bo;


import java.util.List;

/**
 * Created by kiekiyang on 2017/5/5.
 */
public class ProviderChannels /*implements Comparable<ProviderChannels>*/ {
    private int providerId;
    private int weight;
    private int line;
    private List<ProviderAccount> providerAccounts;

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public List<ProviderAccount> getProviderAccounts() {
        return providerAccounts;
    }

    public void setProviderAccounts(List<ProviderAccount> providerAccounts) {
        this.providerAccounts = providerAccounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProviderChannels that = (ProviderChannels) o;

        if (providerId != that.providerId) return false;
        if (weight != that.weight) return false;
        if (line != that.line) return false;
        return providerAccounts != null ? providerAccounts.equals(that.providerAccounts) : that.providerAccounts == null;
    }

    @Override
    public int hashCode() {
        int result = providerId;
        result = 31 * result + weight;
        result = 31 * result + line;
        result = 31 * result + (providerAccounts != null ? providerAccounts.hashCode() : 0);
        return result;
    }


    /*@Override
    public int compareTo(ProviderChannels o) {
        return (this.weight - o.getWeight());
    }*/
}
